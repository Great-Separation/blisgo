package blisgo.infrastructure.external.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import blisgo.infrastructure.external.fixture.FixtureFactory;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.type.TypeReference;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SpringBootTest(classes = WasteDirectDBAdapter.class)
class WasteDirectDBAdapterTest {

    private final FixtureMonkey fixtureMonkey = FixtureFactory.create();

    @Autowired
    public WasteDirectDBAdapter wasteDirectDBAdapter;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Nested
    @DisplayName("findIds 메서드는")
    class findIds {

        final List<Long> expected = fixtureMonkey.giveMe(Long.class, 3);

        @Test
        @DisplayName("올바른 id 목록을 반환한다")
        void ids_returnExpectedIds() {
            given(jdbcTemplate.queryForList(anyString(), eq(Long.class))).willReturn(expected);

            var actual = wasteDirectDBAdapter.findIds();

            then(jdbcTemplate).should().queryForList(anyString(), eq(Long.class));
            assertEquals(expected.size(), actual.size());
            assertTrue(actual.containsAll(expected));
        }

        @Test
        @DisplayName("아무것도 반환하지 않는다")
        void noIds_returnEmptyList() {
            given(jdbcTemplate.queryForList(anyString(), eq(Long.class))).willReturn(List.of());

            var actual = wasteDirectDBAdapter.findIds();

            then(jdbcTemplate).should().queryForList(anyString(), eq(Long.class));
            assertTrue(actual.isEmpty());
        }
    }

    @Nested
    @DisplayName("updateViewCount 메서드는")
    class updateViewCount {

        final long wasteId = fixtureMonkey.giveMeOne(Long.class);

        final long increment = fixtureMonkey.giveMeOne(Long.class);

        @Test
        @DisplayName("행이 업데이트되면 true 반환한다")
        void rowsUpdated_returnTrue() {
            given(jdbcTemplate.update(anyString(), anyLong(), anyLong())).willReturn(1);

            assertTrue(wasteDirectDBAdapter.updateViewCount(wasteId, increment));

            then(jdbcTemplate).should().update(anyString(), anyLong(), anyLong());
        }

        @Test
        @DisplayName("행이 업데이트되지 않으면 false 반환한다")
        void noRowsUpdated_returnFalse() {
            given(jdbcTemplate.update(anyString(), anyLong(), anyLong())).willReturn(0);

            assertFalse(wasteDirectDBAdapter.updateViewCount(wasteId, increment));

            then(jdbcTemplate).should().update(anyString(), anyLong(), anyLong());
        }
    }

    @Nested
    @DisplayName("updatePopularity 메서드는")
    class updatePopularity {

        @Test
        @DisplayName("항상 true 반환한다")
        void always_returnTrue() {
            assertTrue(wasteDirectDBAdapter.updatePopularity());
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class findAll {

        final List<Map<String, String>> expected = fixtureMonkey.giveMe(new TypeReference<>() {}, 3);

        @Test
        @DisplayName("올바른 결과를 반환한다")
        void dataIncluded_returnRows() {
            given(jdbcTemplate.query(anyString(), any(RowMapper.class))).willReturn(expected);

            var actual = wasteDirectDBAdapter.findAll();

            then(jdbcTemplate).should().query(anyString(), any(RowMapper.class));
            assertEquals(expected.size(), actual.size());
        }

        @Test
        @DisplayName("아무것도 반환하지 않는다")
        void noData_returnEmptyList() {
            given(jdbcTemplate.query(anyString(), any(RowMapper.class))).willReturn(List.of());

            var actual = wasteDirectDBAdapter.findAll();

            then(jdbcTemplate).should().query(anyString(), any(RowMapper.class));
            assertTrue(actual.isEmpty());
        }
    }
}
