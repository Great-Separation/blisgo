package blisgo.infrastructure.external.search;

import blisgo.domain.dictionary.Waste;
import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.TypoTolerance;
import com.algolia.search.models.synonyms.Synonym;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlgoliaClient {
    @Value("${algolia.application-id}")
    private String applicationId;

    @Value("${algolia.api-key}")
    private String apiKey;

    @Value("${algolia.index-name}")
    private String indexName;

    private final WasteIndexMapper mapper;

    private SearchIndex<WasteIndex> wasteSearchIndex;

    private static final int MIN_SYNONYM_REQUIRED = 2;

    private void init(Locale locale) {
        try (SearchClient client = DefaultSearchClient.create(applicationId, apiKey)) {
            String localedIndexName = indexName + "_" + locale.getLanguage();
            wasteSearchIndex = client.initIndex(localedIndexName, WasteIndex.class);
            wasteSearchIndex.setSettings(new IndexSettings()
                    .setSearchableAttributes(List.of("name"))
                    .setQueryLanguages(List.of(locale.getLanguage()))
                    .setTypoTolerance(TypoTolerance.of("min"))
            );
        } catch (IOException e) {
            log.error("Failed to initialize Algolia client", e);
        }
    }

    public void batchUpdate(List<Waste> wastes, Locale locale) {
        init(locale);

        var indexes = wastes.stream()
                .map(mapper::toIndex)
                .toList();

        wasteSearchIndex.partialUpdateObjectsAsync(indexes, true);

        List<Synonym> synonyms = new ArrayList<>();
        for (var waste : wastes) {
            Long wasteId = waste.wasteId().id();
            List<String> hashtags = new ArrayList<>(Optional.ofNullable(waste.hashtags())
                    .orElse(List.of()));

            hashtags.add(waste.name());

            if (hashtags.size() < MIN_SYNONYM_REQUIRED) {
                continue;
            }

            synonyms.add(Synonym.createSynonym(String.valueOf(wasteId), hashtags));
        }
        wasteSearchIndex.saveSynonymsAsync(synonyms);
    }
}
