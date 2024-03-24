package blisgo.infrastructure.external.handler;

import blisgo.infrastructure.external.redis.ViewCountCache;
import blisgo.usecase.event.PostViewedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Description("게시물 조회 이벤트 핸들러")
public class PostViewedEventHandler {

    private final ViewCountCache viewCountCache;

    @EventListener
    public void handlePostViewedEvent(PostViewedEvent event) {
        viewCountCache.increaseViewCount(event.postId());
    }
}