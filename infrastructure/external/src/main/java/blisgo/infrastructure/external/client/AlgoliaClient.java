package blisgo.infrastructure.external.client;

import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.vo.Category;
import blisgo.infrastructure.external.base.WasteIndex;
import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.indexing.Query;
import com.algolia.search.models.settings.IndexSettings;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class AlgoliaClient {
    @Value("${algolia.application-id}")
    private String applicationId;

    @Value("${algolia.api-key}")
    private String apiKey;

    @Value("${algolia.index-name}")
    private String indexName;

    private SearchIndex<WasteIndex> wasteSearchIndex;

    @PostConstruct
    private void init() throws IOException {
        try (SearchClient client = DefaultSearchClient.create(applicationId, apiKey)) {
            wasteSearchIndex = client.initIndex(indexName, WasteIndex.class);
            wasteSearchIndex.setSettings(new IndexSettings()
                    .setRanking(List.of("desc(views)", "desc(popularity)"))
            );
        }
    }

    public void updateOrSaveIndex(List<Waste> wastes) {
        for (var waste : wastes) {
            var index = convertToWasteIndex(waste);
            wasteSearchIndex.searchAsync(new Query(String.valueOf(waste.wasteId().id()))).thenAccept(result -> {
                if (result.getHits().isEmpty()) {
                    wasteSearchIndex.saveObjectAsync(index);
                    log.info("Saved waste: {}", waste);
                } else {
                    wasteSearchIndex.partialUpdateObjectAsync(index);
                    log.info("Updated waste: {}", index);
                }
            });
        }
    }

    private WasteIndex convertToWasteIndex(Waste waste) {
        return WasteIndex.builder()
                .objectID(waste.wasteId().id())
                .wasteId(waste.wasteId().id())
                .name(waste.name())
                .type(waste.type())
                .picture(waste.picture().url())
                .views(waste.views())
                .categories(waste.categories().stream().map(Category::tag).toList())
                .createdDate(waste.createdDate())
                .modifiedDate(waste.modifiedDate())
                .build();
    }
}
