package blisgo.usecase.request.post;

import blisgo.domain.community.Post;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.data.domain.Slice;

@PrimaryPort
public interface PostQuery {

    Post getPost(GetPost query);

    Slice<Post> getPosts(GetPost query);
}
