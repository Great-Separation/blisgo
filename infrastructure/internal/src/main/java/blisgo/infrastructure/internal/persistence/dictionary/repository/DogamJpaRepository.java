package blisgo.infrastructure.internal.persistence.dictionary.repository;

import blisgo.infrastructure.internal.persistence.dictionary.model.JpaDogam;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaDogamId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogamJpaRepository extends JpaRepository<JpaDogam, Long> {
    boolean deleteByDogamId(JpaDogamId dogamId);

    boolean existsByDogamId(JpaDogamId dogamId);
}
