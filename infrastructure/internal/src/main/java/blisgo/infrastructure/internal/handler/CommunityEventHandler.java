package blisgo.infrastructure.internal.handler;

import blisgo.domain.community.Post;
import blisgo.domain.community.event.PostAddEvent;
import blisgo.domain.community.event.PostRemoveEvent;
import blisgo.domain.community.event.PostUpdateEvent;
import blisgo.domain.community.event.PostViewEvent;
import blisgo.infrastructure.external.cache.ViewCountCache;
import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.external.storage.CloudinaryClient;
import blisgo.infrastructure.internal.persistence.community.PostPersistenceAdapter;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Slf4j
@Component
@RequiredArgsConstructor
public class CommunityEventHandler {

    private final ViewCountCache viewCountCache;

    private final CloudinaryClient cloudinaryClient;

    private final PostPersistenceAdapter postPersistenceAdapter;

    private final JsonParser jsonParser;

    @EventListener
    public void handlePostViewedEvent(PostViewEvent event) {
        viewCountCache.increaseViewCount(event.postId().id(), "post");
    }

    @EventListener
    public void handlePostAddEvent(PostAddEvent event) {
        String text = event.text();
        List<Path> paths = jsonParser.parseFilenames(text);
        cloudinaryClient.tagAs(paths, "stored");
    }

    @EventListener
    public void handlePostUpdateEvent(PostUpdateEvent event) {
        Post existedPost = postPersistenceAdapter.read(event.postId().id());

        String existedText = existedPost.content().text();
        List<Path> paths1 = jsonParser.parseFilenames(existedText);

        String timeMarkedTemp = "temp".concat(":").concat(LocalDate.now().toString());
        cloudinaryClient.tagAs(paths1, timeMarkedTemp);

        String updatedText = event.text();
        List<Path> paths2 = jsonParser.parseFilenames(updatedText);
        cloudinaryClient.tagAs(paths2, "stored");
    }

    @EventListener
    public void handlePostRemoveEvent(PostRemoveEvent event) {
        Post post = postPersistenceAdapter.read(event.postId().id());

        String text = post.content().text();
        List<Path> paths = jsonParser.parseFilenames(text);

        String timeMarkedTemp = "temp".concat(":").concat(LocalDate.now().toString());
        cloudinaryClient.tagAs(paths, timeMarkedTemp);
    }
}
