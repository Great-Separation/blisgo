package blisgo.infrastructure.internal.persistence.community.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import blisgo.domain.community.Post;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.community.model.JpaPost;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MapperConfig.class, PostMapper.class})
class PostMapperTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final int REPEAT = 10;

    @Autowired
    PostMapper postMapper;

    @Test
    @DisplayName("도메인을 엔티티로 변환")
    void domainToEntity() {
        for (int i = 0; i < REPEAT; i++) {
            var domain = fixtureMonkey.giveMeOne(Post.class);
            assertDoesNotThrow(() -> postMapper.toEntity(domain));
            assertNotNull(domain);
        }
    }

    @Test
    @DisplayName("엔티티를 도메인으로 변환")
    void entityToDomain() {
        for (int i = 0; i < REPEAT; i++) {
            var entity = fixtureMonkey.giveMeOne(JpaPost.class);
            assertDoesNotThrow(() -> postMapper.toDomain(entity));
            assertNotNull(entity);
        }
    }

    @Test
    @DisplayName("도메인를 DTO로 변환")
    void domainToDTO() {
        for (int i = 0; i < REPEAT; i++) {
            var domain = fixtureMonkey.giveMeOne(Post.class);
            assertDoesNotThrow(() -> postMapper.toDTO(domain));
            assertNotNull(domain);
        }
    }
}
