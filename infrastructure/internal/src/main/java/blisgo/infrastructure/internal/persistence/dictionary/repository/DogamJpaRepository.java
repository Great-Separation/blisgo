package blisgo.infrastructure.internal.persistence.dictionary.repository;

import blisgo.infrastructure.internal.persistence.dictionary.model.JpaDogam;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaDogamId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DogamJpaRepository extends JpaRepository<JpaDogam, Long> {
    Optional<JpaDogam> findFirstByDogamId(JpaDogamId dogamId);

    Slice<JpaDogam> findByDogamIdMemberId(UUID memberId, Pageable pageable);

    boolean deleteByDogamId(JpaDogamId dogamId);
}
