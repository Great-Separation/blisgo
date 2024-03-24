package blisgo.infrastructure.internal.persistence.member.repository;

import blisgo.infrastructure.internal.persistence.member.model.JpaMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberJpaRepository extends CrudRepository<JpaMember, UUID> {
    Optional<JpaMember> findFirstByEmail(String email);

    boolean deleteByEmail(String email);
}
