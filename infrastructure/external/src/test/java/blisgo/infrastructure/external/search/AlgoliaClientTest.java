package blisgo.infrastructure.external.search;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.times;

import blisgo.domain.dictionary.Waste;
import blisgo.infrastructure.external.fixture.FixtureFactory;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.settings.IndexSettings;
import com.algolia.search.models.settings.SetSettingsResponse;
import com.navercorp.fixturemonkey.FixtureMonkey;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlgoliaClientTest {

    private final FixtureMonkey fixtureMonkey = FixtureFactory.create();

    @Mock
    private WasteIndexMapper mapper;

    @Mock
    private SearchIndex<WasteIndex> wasteSearchIndex;

    @InjectMocks
    private AlgoliaClient algoliaClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("batchUpdate 메서드는")
    class batchUpdate {

        final List<Waste> wastes = fixtureMonkey.giveMe(Waste.class, 100);

        final Locale locale = Locale.KOREAN;

        @Test
        @DisplayName("정상적으로 업데이트한다")
        void updatesSuccessfully() {
            given(wasteSearchIndex.getSettings()).willReturn(new IndexSettings());
            given(wasteSearchIndex.setSettings(any(IndexSettings.class))).willReturn(new SetSettingsResponse());
            given(wasteSearchIndex.partialUpdateObjectsAsync(anyList(), anyBoolean()))
                    .willReturn(CompletableFuture.completedFuture(null));

            algoliaClient.batchUpdate(wastes, locale);

            then(wasteSearchIndex).should(times(1)).partialUpdateObjectsAsync(anyList(), anyBoolean());
        }

        @Test
        @DisplayName("실제로 동기화를 수행한다")
        void actuallyPerformsSynchronization() {
            given(wasteSearchIndex.getSettings()).willReturn(new IndexSettings());
            given(wasteSearchIndex.setSettings(any(IndexSettings.class))).willReturn(new SetSettingsResponse());
            given(wasteSearchIndex.saveSynonymsAsync(anyList())).willReturn(CompletableFuture.completedFuture(null));

            algoliaClient.batchUpdate(wastes, locale);

            then(wasteSearchIndex).should(times(1)).saveSynonymsAsync(anyList());
        }
    }
}
