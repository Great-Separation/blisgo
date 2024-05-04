package blisgo.infrastructure.internal.handler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.event.WasteViewEvent;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.handler.base.EventsConfig;
import blisgo.usecase.port.domain.WasteInputPort;
import blisgo.usecase.port.domain.WasteOutputPort;
import blisgo.usecase.request.waste.GetWaste;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@ContextConfiguration(classes = EventsConfig.class)
@SpringBootTest(classes = WasteInputPort.class)
@RecordApplicationEvents
class DictionaryEventHandlerTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create(ConstructorPropertiesArbitraryIntrospector.INSTANCE);
    final int REPEAT = 10;

    @MockBean
    private WasteOutputPort wasteOutputPort;

    @Autowired
    private ApplicationEvents events;

    @InjectMocks
    private WasteInputPort wasteInputPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        wasteInputPort = new WasteInputPort(wasteOutputPort);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("폐기물 조회 이벤트 핸들링")
    void handleWasteViewedEvent() {
        given(wasteOutputPort.read(anyLong())).willReturn(any(Waste.class));
        var command = fixtureMonkey
                .giveMeBuilder(GetWaste.class)
                .set("wasteId", Math.abs(RandomGenerator.getDefault().nextLong()))
                .set("pageable", Pageable.ofSize(10))
                .build()
                .sample();

        wasteInputPort.getWaste(command);

        then(wasteOutputPort).should(times(1)).read(anyLong());
        var actual = events.stream(WasteViewEvent.class).findAny().isPresent();
        assertTrue(actual);
    }
}
