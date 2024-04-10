package blisgo.infrastructure.internal.persistence.community;

import blisgo.domain.community.Post;
import blisgo.infrastructure.internal.persistence.community.mapper.PostMapper;
import blisgo.infrastructure.internal.persistence.community.model.JpaPost;
import blisgo.infrastructure.internal.persistence.community.repository.PostCustomRepository;
import blisgo.infrastructure.internal.persistence.community.repository.PostJpaRepository;
import blisgo.usecase.port.domain.PostOutputPort;
import blisgo.usecase.port.infra.ViewCountable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostMySQLAdapter implements PostOutputPort, ViewCountable {
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
        return customRepository.findByIdWithReplies(postId)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public Slice<Post> read(Map<String, ?> columns, Pageable pageable) {
        Long lastPostId = (Long) columns.get("lastPostId");

        return customRepository.find(pageable, lastPostId)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional
    public boolean update(Post domain) {
        jpaRepository.findById(domain.postId().id()).ifPresent(
                existingPost -> existingPost.updateInfo(mapper.toEntity(domain))
        );

        return true;
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

    @Override
    public List<Long> findIds() {
        return customRepository.findPostIds();
    }

    @Override
    @Transactional
    public boolean updateViewCount(Long postId, Long views) {
        return customRepository.updateViewCount(postId, views);
    }
}
