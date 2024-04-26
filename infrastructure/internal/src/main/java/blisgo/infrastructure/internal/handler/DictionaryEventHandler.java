package blisgo.infrastructure.internal.handler;

import blisgo.domain.dictionary.event.WasteViewEvent;
import blisgo.infrastructure.external.redis.ViewCountCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DictionaryEventHandler {
    private final ViewCountCache viewCountCache;

    @EventListener
    public void handleWasteViewedEvent(WasteViewEvent event) {
        viewCountCache.increaseViewCount(event.wasteId(), "waste");
    }

}
