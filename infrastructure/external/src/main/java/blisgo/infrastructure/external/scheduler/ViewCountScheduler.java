package blisgo.infrastructure.external.scheduler;

import blisgo.infrastructure.external.redis.ViewCountCache;
import blisgo.infrastructure.internal.persistence.community.PostMySQLAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Description("캐시에 저장된 조회수를 DB에 갱신하는 스케줄러")
public class ViewCountScheduler {
    private final ViewCountCache viewCountCache;
    private final PostMySQLAdapter postMySQLAdapter;

    @Scheduled(fixedRate = 60000)
    public void updateViewCounts() {
        List<Long> postIds = postMySQLAdapter.findPostIds();

        for (Long postId : postIds) {
            long viewCount = viewCountCache.getViewCount(postId);
            if (viewCount > 0 && (postMySQLAdapter.updateViewCount(postId, viewCount))) {
                viewCountCache.remove(postId);
            }
        }
    }
}