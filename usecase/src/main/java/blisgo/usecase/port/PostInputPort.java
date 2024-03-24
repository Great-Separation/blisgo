package blisgo.usecase.port;

import blisgo.domain.community.Post;
import blisgo.usecase.base.Events;
import blisgo.usecase.event.PostViewedEvent;
import blisgo.usecase.request.post.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostInputPort implements PostCommand, PostQuery {
    private final PostOutputPort port;

    @Override
    public boolean addPost(AddPost command) {
        var post = Post.create(
                command.title(),
                command.content()
        );

        return port.create(post);
    }

    @Override
    public boolean updatePost(UpdatePost command) {
        var post = Post.create(
                command.postId(),
                command.title(),
                command.content()
        );

        return port.update(post);
    }

    @Override
    public boolean removePost(RemovePost command) {
        var postId = command.postId();

        return port.delete(postId);
    }

    @Override
    public boolean like(PostLike command) {
        Long postId = command.postId();
        Boolean isLike = command.isLike();

        return port.updateLike(postId, isLike);
    }

    @Override
    public Post getPost(GetPost query) {
        Events.raise(new PostViewedEvent(query.postId()));
        return port.read(query.postId());
    }

    @Override
    public Slice<Post> getPosts(GetPost query) {
        return port.read(Map.of("lastPostId", query.postId()), query.pageable());
    }
}
