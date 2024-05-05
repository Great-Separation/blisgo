package blisgo.infrastructure.internal.persistence.community.mapper;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import blisgo.domain.community.Reply;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.community.model.JpaReply;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MapperConfig.class, ReplyMapper.class})
class ReplyMapperTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final int REPEAT = 10;

    @Autowired
    ReplyMapper replyMapper;

    @Test
    @DisplayName("도메인을 엔티티로 변환")
    void domainToEntity() {
        for (int i = 0; i < REPEAT; i++) {
            var domain = fixtureMonkey.giveMeOne(Reply.class);
            assertDoesNotThrow(() -> replyMapper.toEntity(domain));
            assertNotNull(domain);
        }
    }

    @Test
    @DisplayName("엔티티를 도메인으로 변환")
    void entityToDomain() {
        for (int i = 0; i < REPEAT; i++) {
            var entity = fixtureMonkey.giveMeOne(JpaReply.class);
            assertDoesNotThrow(() -> replyMapper.toDomain(entity));
            assertNotNull(entity);
        }
    }

    @Test
    @DisplayName("도메인을 DTO로 변환")
    void domainToDTO() {
        for (int i = 0; i < REPEAT; i++) {
            var domain = fixtureMonkey.giveMeOne(Reply.class);
            assertDoesNotThrow(() -> replyMapper.toDTO(domain));
            assertNotNull(domain);
        }
    }
}
