package blisgo.infrastructure.external.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import blisgo.infrastructure.external.fixture.FixtureFactory;
import com.navercorp.fixturemonkey.FixtureMonkey;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(classes = PostDirectDBAdapter.class)
class PostDirectDBAdapterTest {

    private final FixtureMonkey fixtureMonkey = FixtureFactory.create();

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostDirectDBAdapter postDirectDBAdapter;

    @Nested
    @DisplayName("findIds 메서드는")
    class findIds {

        final List<Long> expected = fixtureMonkey.giveMe(Long.class, 10);

        @Test
        @DisplayName("findIds는 올바른 id 목록을 반환한다")
        void _returnCorrectIds() {
            given(jdbcTemplate.queryForList(anyString(), eq(Long.class))).willReturn(expected);

            var actual = postDirectDBAdapter.findIds();

            assertEquals(expected, actual);
            then(jdbcTemplate).should().queryForList(anyString(), eq(Long.class));
        }
    }

    @Nested
    @DisplayName("updateViewCount 메서드는")
    class updateViewCount {

        final long increment = fixtureMonkey.giveMeOne(Long.class);

        final long postId = fixtureMonkey.giveMeOne(Long.class);

        @Test
        @DisplayName("updateViewCount는 업데이트가 성공하면 true를 반환한다")
        void success_returnTrue() {
            given(jdbcTemplate.update(anyString(), anyLong(), anyLong())).willReturn(1);

            var actual = postDirectDBAdapter.updateViewCount(postId, increment);

            assertTrue(actual);
            then(jdbcTemplate).should().update(anyString(), anyLong(), anyLong());
        }

        @Test
        @DisplayName("updateViewCount는 업데이트가 실패하면 false를 반환한다")
        void fail_returnFalse() {
            given(jdbcTemplate.update(anyString(), anyLong(), anyLong())).willReturn(0);

            var actual = postDirectDBAdapter.updateViewCount(postId, increment);

            assertFalse(actual);
        }
    }
}
