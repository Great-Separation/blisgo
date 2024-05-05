package blisgo.infrastructure.internal.persistence.dictionary.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import blisgo.domain.dictionary.vo.Guide;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaGuide;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MapperConfig.class, GuideMapper.class})
class GuideMapperTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final int REPEAT = 10;

    @Autowired
    GuideMapper guideMapper;

    @Test
    @DisplayName("도메인을 엔티티로 변환")
    void domainToEntity() {
        for (int i = 0; i < REPEAT; i++) {
            var domain = fixtureMonkey.giveMeOne(Guide.class);
            assertDoesNotThrow(() -> guideMapper.toEntity(domain));
            assertNotNull(domain);
        }
    }

    @Test
    @DisplayName("엔티티를 도메인으로 변환")
    void entityToDomain() {
        for (int i = 0; i < REPEAT; i++) {
            var entity = fixtureMonkey.giveMeOne(JpaGuide.class);
            assertDoesNotThrow(() -> guideMapper.toDomain(entity));
            assertNotNull(entity);
        }
    }

    @Test
    @DisplayName("도메인를 DTO로 변환")
    void domainToDTO() {
        for (int i = 0; i < REPEAT; i++) {
            var entity = fixtureMonkey.giveMeOne(Guide.class);
            assertDoesNotThrow(() -> guideMapper.toDTO(entity));
            assertNotNull(entity);
        }
    }
}
