package blisgo.infrastructure.internal.handler;

import blisgo.domain.community.Post;
import blisgo.domain.community.event.PostAddEvent;
import blisgo.domain.community.event.PostRemoveEvent;
import blisgo.domain.community.event.PostUpdateEvent;
import blisgo.domain.community.event.PostViewEvent;
import blisgo.infrastructure.external.client.CloudinaryClient;
import blisgo.infrastructure.external.redis.ViewCountCache;
import blisgo.infrastructure.internal.persistence.community.PostPersistenceAdapter;
import blisgo.infrastructure.internal.ui.base.ContentParser;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommunityEventHandler {
    private final ViewCountCache viewCountCache;
    private final CloudinaryClient cloudinaryClient;
    private final PostPersistenceAdapter postPersistenceAdapter;

    private static final String TEMP = "temp";
    private static final String BOARD = "board";

    @Async
    @EventListener
    public void handlePostViewedEvent(PostViewEvent event) {
        viewCountCache.increaseViewCount(event.postId().id(), "post");
    }

    @Async
    @EventListener
    public void handlePostAddEvent(PostAddEvent event) {
        String text = event.text();
        parseFilePathsAndChangeStorePath(text, TEMP, BOARD);
    }

    @Async
    @EventListener
    public void handlePostUpdateEvent(PostUpdateEvent event) {
        Post existedPost = postPersistenceAdapter.read(event.postId().id());

        String existedText = existedPost.content().text();
        parseFilePathsAndChangeStorePath(existedText, BOARD, TEMP);

        String updatedText = event.text();
        parseFilePathsAndChangeStorePath(updatedText, TEMP, BOARD);
    }

    @Async
    @EventListener
    public void handlePostRemoveEvent(PostRemoveEvent event) {
        Post post = postPersistenceAdapter.read(event.postId().id());

        String text = post.content().text();
        parseFilePathsAndChangeStorePath(text, BOARD, TEMP);
    }

    private void parseFilePathsAndChangeStorePath(String text, String from, String to) {
        JsonNode json = ContentParser.toJson(text);
        List<Path> paths = ContentParser.parseFilenames(json);

        paths.forEach(path -> cloudinaryClient.moveFile(path, from, to));
    }

}
