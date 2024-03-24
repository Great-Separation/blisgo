package blisgo.infrastructure.internal.persistence.dictionary.repository;

import blisgo.infrastructure.internal.persistence.dictionary.model.JpaWaste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WasteJpaRepository extends JpaRepository<JpaWaste, Long> {
    Optional<JpaWaste> findFirstByWasteId(Long wasteId);
}
