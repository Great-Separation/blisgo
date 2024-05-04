package blisgo.infrastructure.internal.persistence.community.repository;

import blisgo.infrastructure.internal.persistence.community.model.JpaPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<JpaPost, Long> {

    long deleteByPostId(Long identifier);
}
