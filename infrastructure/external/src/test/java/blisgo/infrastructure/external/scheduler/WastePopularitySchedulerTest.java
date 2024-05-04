package blisgo.infrastructure.external.scheduler;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import blisgo.infrastructure.external.database.WasteDirectDBAdapter;
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
class WastePopularitySchedulerTest {

    @Mock
    WasteDirectDBAdapter wasteMySQLAdapter;

    @InjectMocks
    WastePopularityScheduler wastePopularityScheduler;

    @Nested
    @DisplayName("updateWastePopularity 메서드는")
    class updateWastePopularity {

        @Test
        @DisplayName("폐기물 인기도 업데이트 성공시 아무것도 반환하지 않는다")
        void success_void() {
            given(wasteMySQLAdapter.updatePopularity()).willReturn(true);

            wastePopularityScheduler.updateWastePopularity();

            then(wasteMySQLAdapter).should(times(1)).updatePopularity();
        }

        @Test
        @DisplayName("폐기물 인기도 업데이트 실패시 아무것도 반환하지 않는다")
        void fail_void() {
            given(wasteMySQLAdapter.updatePopularity()).willReturn(false);

            wastePopularityScheduler.updateWastePopularity();

            then(wasteMySQLAdapter).should(times(1)).updatePopularity();
        }
    }
}
