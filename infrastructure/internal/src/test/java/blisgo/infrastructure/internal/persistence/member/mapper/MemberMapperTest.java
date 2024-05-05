package blisgo.infrastructure.internal.persistence.member.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import blisgo.domain.member.Member;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.member.model.JpaMember;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MapperConfig.class, MemberMapper.class})
class MemberMapperTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final int REPEAT = 10;

    @Autowired
    MemberMapper memberMapper;

    @Test
    @DisplayName("도메인을 엔티티로 변환")
    void domainToEntity() {
        for (int i = 0; i < REPEAT; i++) {
            var domain = fixtureMonkey.giveMeOne(Member.class);
            assertDoesNotThrow(() -> memberMapper.toEntity(domain));
            assertNotNull(domain);
        }
    }

    @Test
    @DisplayName("엔티티를 도메인으로 변환")
    void entityToDomain() {
        for (int i = 0; i < REPEAT; i++) {
            var entity = fixtureMonkey.giveMeOne(JpaMember.class);
            assertDoesNotThrow(() -> memberMapper.toDomain(entity));
            assertNotNull(entity);
        }
    }

    @Test
    @DisplayName("도메인을 DTO로 변환")
    void domainToDTO() {
        for (int i = 0; i < REPEAT; i++) {
            var domain = fixtureMonkey.giveMeOne(Member.class);
            assertDoesNotThrow(() -> memberMapper.toDTO(domain));
            assertNotNull(domain);
        }
    }
}
