package blisgo.infrastructure.internal.persistence.dictionary.repository;

import blisgo.domain.dictionary.vo.Category;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaGuide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideJpaRepository extends JpaRepository<JpaGuide, Category> {
}
