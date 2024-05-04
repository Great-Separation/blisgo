package blisgo.infrastructure.internal.persistence.community;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import blisgo.domain.community.Post;
import blisgo.domain.community.vo.PostId;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.JpaConfig;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.community.mapper.PostMapper;
import blisgo.infrastructure.internal.persistence.community.repository.PostCustomRepository;
import blisgo.infrastructure.internal.persistence.community.repository.PostJpaRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import java.util.Map;
import java.util.random.RandomGenerator;
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
            PostPersistenceAdapter.class,
            PostCustomRepository.class,
            PostMapper.class,
            JpaConfig.class,
            MapperConfig.class
        })
@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = "TRUNCATE TABLE post RESTART IDENTITY;")
class PostPersistenceAdapterTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create(FieldReflectionArbitraryIntrospector.INSTANCE);
    final int REPEAT = 10;

    @Autowired
    PostJpaRepository postJpaRepository;

    @Autowired
    PostCustomRepository postCustomRepository;

    @Autowired
    PostMapper postMapper;

    PostPersistenceAdapter postPersistenceAdapter;

    @BeforeEach
    public void setup() {
        postPersistenceAdapter = new PostPersistenceAdapter(postJpaRepository, postCustomRepository, postMapper);
    }

    @Nested
    class C {

        @Test
        @DisplayName("게시글 생성 테스트")
        void create() {
            Post post = fixtureMonkey
                    .giveMeBuilder(Post.class)
                    .set("postId", PostId.of(RandomGenerator.getDefault().nextLong(1, 11)))
                    .sample();

            var actual = postPersistenceAdapter.create(post);

            assertTrue(actual);
        }
    }

    @Nested
    class RUD {

        long postId = 1L;

        @BeforeEach
        void setup() {
            Post post = fixtureMonkey.giveMeOne(Post.class);
            postPersistenceAdapter.create(post);
        }

        @RepeatedTest(REPEAT)
        @DisplayName("게시글 조회 테스트(단건)")
        void read() {
            assertNotNull(postPersistenceAdapter.read(postId));
        }

        @RepeatedTest(REPEAT)
        @DisplayName("게시글 조회 테스트(다건)")
        void testRead() {
            var column = "lastPostId";
            var lastPostId = RandomGenerator.getDefault().nextLong(1, 11);

            assertNotNull(postPersistenceAdapter.read(Map.of(column, lastPostId), Pageable.ofSize(10)));
        }

        @Test
        @DisplayName("게시글 수정 테스트")
        void update() {
            var expected = postPersistenceAdapter.read(postId);

            Post updatePost = expected.toBuilder().title("update title").build();

            var actual = postPersistenceAdapter.update(updatePost);
            assertTrue(actual);
        }

        @Test
        @DisplayName("게시글 좋아요 테스트")
        void updateLike() {
            boolean isLike = fixtureMonkey.giveMeOne(boolean.class);

            var actual = postPersistenceAdapter.updateLike(postId, isLike);
            assertTrue(actual);
        }

        @Test
        @DisplayName("게시글 삭제 테스트")
        void delete() {
            assertTrue(postPersistenceAdapter.delete(postId));
        }
    }
}
