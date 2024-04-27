package blisgo.usecase.port.domain;

import blisgo.domain.base.Events;
import blisgo.domain.common.Content;
import blisgo.domain.common.Picture;
import blisgo.domain.community.Post;
import blisgo.domain.community.event.PostAddEvent;
import blisgo.domain.community.event.PostRemoveEvent;
import blisgo.domain.community.event.PostUpdateEvent;
import blisgo.domain.community.event.PostViewEvent;
import blisgo.domain.community.vo.PostId;
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
        Content content = Content.of(
                command.content(),
                Picture.of(command.thumbnail()),
                command.preview()
        );

        Events.raise(new PostAddEvent(command.content()));

        var post = Post.create(
                command.title(),
                content,
                command.color()
        );

        return port.create(post);
    }

    @Override
    public boolean updatePost(UpdatePost command) {
        Content content = Content.of(
                command.content(),
                Picture.of(command.thumbnail()),
                command.preview()
        );

        Events.raise(new PostUpdateEvent(PostId.of(command.postId()), command.content()));

        var post = Post.create(
                command.postId(),
                command.title(),
                content,
                command.color()
        );

        return port.update(post);
    }

    @Override
    public boolean removePost(RemovePost command) {
        var postId = command.postId();

        Events.raise(new PostRemoveEvent(PostId.of(postId)));

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
        Long postId = query.postId();

        Events.raise(new PostViewEvent(PostId.of(postId)));

        return port.read(query.postId());
    }

    @Override
    public Slice<Post> getPosts(GetPost query) {
        return port.read(Map.of("lastPostId", query.postId()), query.pageable());
    }
}
