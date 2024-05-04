package blisgo.infrastructure.external.scheduler;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;

import blisgo.infrastructure.external.cache.ViewCountCache;
import blisgo.infrastructure.external.database.PostDirectDBAdapter;
import blisgo.infrastructure.external.database.WasteDirectDBAdapter;
import blisgo.infrastructure.external.fixture.FixtureMonkeySingleton;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

@Import(SchedulerConfig.class)
@ExtendWith(MockitoExtension.class)
class ViewCountSchedulerTest {

    private final FixtureMonkey fixtureMonkey = FixtureMonkeySingleton.getInstance();

    @Mock
    private ViewCountCache viewCountCache;

    @Mock
    private PostDirectDBAdapter postDirectDBAdapter;

    @Mock
    private WasteDirectDBAdapter wasteDirectDBAdapter;

    @InjectMocks
    private ViewCountScheduler viewCountScheduler;

    @Nested
    @DisplayName("updateViewCounts 메서드는")
    class updateViewCounts {

        final int size = 100;

        @Test
        @DisplayName("뷰 카운트를 업데이트한다")
        void success_void() {
            var expected = fixtureMonkey.giveMe(Long.class, size);
            given(postDirectDBAdapter.findIds()).willReturn(expected);
            given(wasteDirectDBAdapter.findIds()).willReturn(expected);
            given(viewCountCache.getViewCount(anyLong(), anyString())).willReturn(1L);

            viewCountScheduler.updateViewCounts();

            then(postDirectDBAdapter).should(times(size)).updateViewCount(anyLong(), anyLong());
            then(wasteDirectDBAdapter).should(times(size)).updateViewCount(anyLong(), anyLong());
        }
    }
}
