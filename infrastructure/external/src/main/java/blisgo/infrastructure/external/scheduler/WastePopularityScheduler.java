package blisgo.infrastructure.external.scheduler;

import blisgo.infrastructure.internal.persistence.dictionary.WasteMySQLAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Description("쓰레기 인기도를 갱신하는 스케줄러")
public class WastePopularityScheduler {
    private final WasteMySQLAdapter wasteMySQLAdapter;

    @Scheduled(zone = "UTC", cron = "0 0 0 * * *")
    public void updateWastePopularity() {
        wasteMySQLAdapter.updateWastePopularity();
    }
}
