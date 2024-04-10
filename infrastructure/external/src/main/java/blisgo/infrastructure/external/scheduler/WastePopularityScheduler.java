package blisgo.infrastructure.external.scheduler;

import blisgo.infrastructure.internal.persistence.dictionary.WasteMySQLAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Description("쓰레기 인기도를 갱신하는 스케줄러")
public class WastePopularityScheduler {
    private final WasteMySQLAdapter wasteMySQLAdapter;

    @Scheduled(zone = "UTC", cron = "0 */1 * * * *")
    public void updateWastePopularity() {
        if (!wasteMySQLAdapter.updatePopularity()) {
            log.error("페기물 인기도 갱신에 실패했습니다.");
        }
    }
}
