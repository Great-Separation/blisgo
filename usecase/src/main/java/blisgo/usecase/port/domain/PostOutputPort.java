package blisgo.usecase.port.domain;

import blisgo.domain.community.Post;
import java.util.Map;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@SecondaryPort
public interface PostOutputPort {

    boolean create(Post domain);

    Post read(Long postId);

    Slice<Post> read(Map<String, ?> columns, Pageable pageable);

    boolean update(Post domain);

    boolean delete(Long postId);

    boolean updateLike(Long postId, Boolean isLike);
}
