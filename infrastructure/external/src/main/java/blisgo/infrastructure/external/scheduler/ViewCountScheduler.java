package blisgo.infrastructure.external.scheduler;

import blisgo.infrastructure.external.redis.ViewCountCache;
import blisgo.infrastructure.internal.persistence.community.PostMySQLAdapter;
import blisgo.infrastructure.internal.persistence.dictionary.WasteMySQLAdapter;
import blisgo.usecase.port.infra.ViewCountable;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Description("캐시에 저장된 조회수를 DB에 갱신하는 스케줄러")
public class ViewCountScheduler {
    private final ViewCountCache viewCountCache;
    private final PostMySQLAdapter postMySQLAdapter;
    private final WasteMySQLAdapter wasteMySQLAdapter;

    @Scheduled(fixedRate = 60000)
    public void updateViewCounts() {
        handleViewCount(postMySQLAdapter, "post");
        handleViewCount(wasteMySQLAdapter, "waste");
    }

    private void handleViewCount(ViewCountable updater, String domain) {
        for (var id : updater.findIds()) {
            long viewCount = viewCountCache.getViewCount(id, domain);

            if (viewCount > 0 && updater.updateViewCount(id, viewCount)) {
                viewCountCache.remove(id, domain);
            }
        }
    }
}