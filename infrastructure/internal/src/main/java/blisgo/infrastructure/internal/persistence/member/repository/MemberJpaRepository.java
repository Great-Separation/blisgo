package blisgo.infrastructure.internal.persistence.member.repository;

import blisgo.infrastructure.internal.persistence.member.model.JpaMember;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MemberJpaRepository extends CrudRepository<JpaMember, UUID> {

    Optional<JpaMember> findFirstByEmail(String email);

    long deleteByEmail(String email);
}
