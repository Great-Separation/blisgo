package blisgo.infrastructure.internal.persistence.community;

import blisgo.domain.community.Post;
import blisgo.infrastructure.internal.persistence.community.mapper.PostMapper;
import blisgo.infrastructure.internal.persistence.community.model.JpaPost;
import blisgo.infrastructure.internal.persistence.community.repository.PostCustomRepository;
import blisgo.infrastructure.internal.persistence.community.repository.PostJpaRepository;
import blisgo.usecase.port.domain.PostOutputPort;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostPersistenceAdapter implements PostOutputPort {

    private final PostJpaRepository jpaRepository;

    private final PostCustomRepository customRepository;

    private final PostMapper mapper;

    @Override
    @Transactional
    public boolean create(Post domain) {
        JpaPost jpaPost = mapper.toEntity(domain);

        jpaRepository.save(jpaPost);
        return true;
    }

    @Override
    public Post read(Long postId) {
        return customRepository
                .findByIdWithReplies(postId)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public Slice<Post> read(Map<String, ?> columns, Pageable pageable) {
        Long lastPostId = (Long) columns.get("lastPostId");

        return customRepository.find(pageable, lastPostId).map(mapper::toDomain);
    }

    @Override
    @Transactional
    public boolean update(Post domain) {
        Long postId = domain.postId().id();

        return jpaRepository
                .findById(postId)
                .map(o -> {
                    o.updateInfo(mapper.toEntity(domain));
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public boolean delete(Long identifier) {
        return jpaRepository.deleteByPostId(identifier) > 0;
    }

    @Transactional
    public boolean updateLike(Long postId, Boolean isLike) {
        return customRepository.updateLike(postId, isLike);
    }
}
