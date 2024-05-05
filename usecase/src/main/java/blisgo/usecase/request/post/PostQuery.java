package blisgo.usecase.request.post;

import blisgo.domain.community.Post;
import blisgo.domain.community.validation.GetPostValid;
import blisgo.domain.community.validation.GetPostsValid;
import jakarta.validation.Valid;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;

@Validated
@PrimaryPort
public interface PostQuery {

    @Valid
    @Validated(GetPostValid.class)
    Post getPost(@Valid GetPost query);

    @Validated(GetPostsValid.class)
    Slice<@Valid Post> getPosts(@Valid GetPosts query);
}
