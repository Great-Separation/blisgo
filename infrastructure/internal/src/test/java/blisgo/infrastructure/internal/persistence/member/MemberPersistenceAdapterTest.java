package blisgo.infrastructure.internal.persistence.member;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import blisgo.domain.member.Member;
import blisgo.domain.member.vo.MemberId;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.JpaConfig;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.member.mapper.MemberMapper;
import blisgo.infrastructure.internal.persistence.member.repository.MemberJpaRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@ContextConfiguration(
        classes = {
            JacksonConfig.class,
            JsonParser.class,
            Jackson2ObjectMapperBuilder.class,
            MemberPersistenceAdapter.class,
            MemberMapper.class,
            JpaConfig.class,
            MapperConfig.class
        })
@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = "TRUNCATE TABLE member;")
class MemberPersistenceAdapterTest {

    private final int REPEAT = 10;
    FixtureMonkey fixtureMonkey = FixtureFactory.create(FieldReflectionArbitraryIntrospector.INSTANCE);

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private MemberMapper memberMapper;

    private MemberPersistenceAdapter memberPersistenceAdapter;

    @BeforeEach
    public void setup() {
        memberPersistenceAdapter = new MemberPersistenceAdapter(memberJpaRepository, memberMapper);
    }

    @Nested
    class CRUD {

        final String email = "okjaeook98@gmail.com";

        @BeforeEach
        public void setup() {
            Member member = fixtureMonkey
                    .giveMeBuilder(Member.class)
                    .set("memberId", MemberId.of(email))
                    .set("email", email)
                    .sample();

            memberPersistenceAdapter.create(member);
        }

        @Test
        @DisplayName("회원 생성")
        void create() {
            Member member = fixtureMonkey
                    .giveMeBuilder(Member.class)
                    .set("memberId", MemberId.of(email))
                    .set("email", email)
                    .sample();

            var actual = memberPersistenceAdapter.create(member);
            assertTrue(actual);
        }

        @Test
        @DisplayName("회원 조회")
        void read() {
            assertNotNull(memberPersistenceAdapter.read(Map.of("email", email)));
        }

        @Test
        @DisplayName("회원 수정")
        void update() {
            Member member = fixtureMonkey
                    .giveMeBuilder(Member.class)
                    .set("memberId", MemberId.of(email))
                    .set("email", email)
                    .sample();

            var actual = memberPersistenceAdapter.update(member);
            assertTrue(actual);
        }

        @Test
        @DisplayName("회원 삭제")
        void delete() {
            var actual = memberPersistenceAdapter.delete(email);
            assertTrue(actual);
        }
    }
}
