package blisgo.infrastructure.external.scheduler;

import blisgo.infrastructure.external.client.AlgoliaClient;
import blisgo.infrastructure.internal.persistence.dictionary.WasteMySQLAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Description("검색 인덱스를 갱신하는 스케줄러")
public class SearchIndexScheduler {
    private final WasteMySQLAdapter adapter;
    private final AlgoliaClient client;

    @Scheduled(zone = "UTC", cron = "0 0 0 * * *")
    public void updateSearchIndex() {
        var wastes = adapter.read();

        client.updateOrSaveIndex(wastes);
    }

}
