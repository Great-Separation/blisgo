package blisgo.usecase.port.domain;

import blisgo.domain.community.Post;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.io.File;
import java.util.List;
import java.util.Map;

@SecondaryPort
public interface PostOutputPort {
    boolean create(Post domain);

    Post read(Long postId);

    Slice<Post> read(Map<String, ?> columns, Pageable pageable);

    boolean update(Post domain);

    boolean delete(Long identifier);

    boolean updateLike(Long postId, Boolean isLike);

    List<Long> findPostIds();
}
