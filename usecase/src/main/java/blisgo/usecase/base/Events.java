package blisgo.usecase.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Events {
    private static ApplicationEventPublisher publisher;

    static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Object event) {
        if (publisher != null) {
            publisher.publishEvent(event);
        }
    }
}