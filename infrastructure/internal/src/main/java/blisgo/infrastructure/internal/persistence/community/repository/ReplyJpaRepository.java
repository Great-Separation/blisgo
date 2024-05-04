package blisgo.infrastructure.internal.persistence.community.repository;

import blisgo.infrastructure.internal.persistence.community.model.JpaReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyJpaRepository extends JpaRepository<JpaReply, Long> {

    long deleteByReplyId(Long identifier);
}
