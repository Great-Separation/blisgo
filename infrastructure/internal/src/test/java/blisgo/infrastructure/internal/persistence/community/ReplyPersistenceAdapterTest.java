package blisgo.infrastructure.internal.persistence.community;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import blisgo.domain.community.Reply;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.JpaConfig;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.community.mapper.ReplyMapper;
import blisgo.infrastructure.internal.persistence.community.repository.ReplyCustomRepository;
import blisgo.infrastructure.internal.persistence.community.repository.ReplyJpaRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
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
            ReplyPersistenceAdapter.class,
            ReplyCustomRepository.class,
            ReplyMapper.class,
            JpaConfig.class,
            MapperConfig.class
        })
@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = "TRUNCATE TABLE reply RESTART IDENTITY;")
class ReplyPersistenceAdapterTest {

    private final int REPEAT = 10;
    FixtureMonkey fixtureMonkey = FixtureFactory.create();

    @Autowired
    private ReplyJpaRepository replyJpaRepository;

    @Autowired
    private ReplyCustomRepository replyCustomRepository;

    @Autowired
    private ReplyMapper replyMapper;

    private ReplyPersistenceAdapter replyPersistenceAdapter;

    @BeforeEach
    public void setup() {
        replyPersistenceAdapter = new ReplyPersistenceAdapter(replyJpaRepository, replyCustomRepository, replyMapper);
    }

    @Test
    @DisplayName("댓글 생성")
    void create() {
        Reply reply = fixtureMonkey.giveMeOne(Reply.class);
        assertTrue(replyPersistenceAdapter.create(reply));
    }

    @Nested
    @DisplayName("댓글 읽기, 삭제")
    class RD {

        long replyId = 1L;

        Pageable pageable = Pageable.ofSize(10);

        long lastReplyId = 1L;

        @BeforeEach
        void setup() {
            create();
        }

        @RepeatedTest(REPEAT)
        @DisplayName("댓글 읽기")
        void read() {
            assertNotNull(replyPersistenceAdapter.read(replyId, pageable, lastReplyId));
        }

        @Test
        @DisplayName("댓글 삭제")
        void delete() {
            assertTrue(replyPersistenceAdapter.delete(replyId));
        }
    }
}
