package blisgo.infrastructure.external.search;

import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.vo.WasteId;
import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.TypoTolerance;
import com.algolia.search.models.synonyms.Synonym;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class AlgoliaClient {

    private static final int MIN_SYNONYM_REQUIRED = 2;
    private final WasteIndexMapper mapper;

    @Value("${algolia.application-id}")
    private String applicationId;

    @Value("${algolia.api-key}")
    private String apiKey;

    @Value("${algolia.index-name}")
    private String indexName;

    private SearchIndex<WasteIndex> wasteSearchIndex;

    @PostConstruct
    private void init() {
        Locale locale = LocaleContextHolder.getLocale();
        try (SearchClient client = DefaultSearchClient.create(applicationId, apiKey)) {
            String localedIndexName = indexName + "_" + locale.getLanguage();
            wasteSearchIndex = client.initIndex(localedIndexName, WasteIndex.class);
            wasteSearchIndex.setSettings(new IndexSettings()
                    .setSearchableAttributes(List.of("name"))
                    .setTypoTolerance(TypoTolerance.of("min")));
        } catch (IOException e) {
            log.error("Failed to initialize Algolia client", e);
        }
    }

    public void batchUpdate(List<Waste> wastes, Locale locale) {
        wasteSearchIndex.setSettings(wasteSearchIndex.getSettings().setQueryLanguages(List.of(locale.getLanguage())));

        var indexes = wastes.stream().map(mapper::toIndex).toList();

        wasteSearchIndex.partialUpdateObjectsAsync(indexes, true);

        List<Synonym> synonyms = new ArrayList<>();
        for (var waste : wastes) {
            WasteId wasteId = waste.wasteId();

            List<String> hashtags = Optional.ofNullable(waste.hashtags()).orElse(new ArrayList<>());
            hashtags.add(waste.name());

            if (wasteId == null || hashtags.size() < MIN_SYNONYM_REQUIRED) {
                continue;
            }

            synonyms.add(Synonym.createSynonym(String.valueOf(wasteId.id()), hashtags));
        }
        wasteSearchIndex.saveSynonymsAsync(synonyms);
    }
}
