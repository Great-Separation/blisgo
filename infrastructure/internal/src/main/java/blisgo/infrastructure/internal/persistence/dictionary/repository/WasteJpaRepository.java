package blisgo.infrastructure.internal.persistence.dictionary.repository;

import blisgo.infrastructure.internal.persistence.dictionary.model.JpaWaste;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteJpaRepository extends JpaRepository<JpaWaste, Long> {

    Optional<JpaWaste> findFirstByWasteId(Long wasteId);
}
