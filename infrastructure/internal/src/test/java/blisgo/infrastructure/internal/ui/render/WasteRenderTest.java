package blisgo.infrastructure.internal.ui.render;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import blisgo.domain.dictionary.Waste;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.GuideMapper;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.infrastructure.internal.ui.base.UIToast;
import blisgo.usecase.request.dogam.DogamQuery;
import blisgo.usecase.request.waste.WasteQuery;
import com.navercorp.fixturemonkey.FixtureMonkey;
import java.util.List;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
@ContextConfiguration(classes = {WasteRender.class, JacksonConfig.class, MapperConfig.class, JsonParser.class})
@MockBeans({@MockBean(JpaMetamodelMappingContext.class)})
@SpyBeans({@SpyBean(UIToast.class), @SpyBean(WasteMapper.class), @SpyBean(GuideMapper.class)})
class WasteRenderTest extends Router {
    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    @MockBean
    WasteQuery wasteQuery;

    @MockBean
    DogamQuery dogamQuery;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(print())
                .build();

        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("GET /wastes")
    class getWastes {
        Long lastWasteId = randomGenerator.nextLong(0, Long.MAX_VALUE);
        List<Waste> wastes = fixtureMonkey.giveMe(Waste.class, randomGenerator.nextInt(10, 101));
        Slice<Waste> slice = new SliceImpl<>(wastes);

        @Test
        @DisplayName("lastWasteId 보다 큰 값을 모두 가져온다")
        void lastWasteId_ok() throws Exception {
            var request = MockMvcRequestBuilders.get("/wastes").param("lastWasteId", lastWasteId.toString());
            given(wasteQuery.getWastes(any())).willReturn(slice);

            var expected = routes(Folder.DICTIONARY, Page.CATALOGUE) + fragment(Fragment.WASTES);

            mvc.perform(request).andExpect(status().isOk()).andExpect(view().name(expected));
        }
    }

    @Nested
    @DisplayName("GET /wastes/{wasteId}")
    class getWaste {
        Long wasteId = randomGenerator.nextLong(1, Long.MAX_VALUE);
        Waste waste = fixtureMonkey.giveMeOne(Waste.class);

        @Test
        @DisplayName("wasteId에 해당하는 정보를 가져온다")
        void wasteId_ok() throws Exception {
            var request = MockMvcRequestBuilders.get("/wastes/" + wasteId);
            given(wasteQuery.getWaste(any())).willReturn(waste);
            given(wasteQuery.getGuides(any())).willReturn(List.of());
            given(wasteQuery.getWastesRelated(any())).willReturn(List.of());

            var expected = routes(Folder.DICTIONARY, Page.INFO) + fragment(Fragment.WASTE);

            mvc.perform(request).andExpect(status().isOk()).andExpect(view().name(expected));
        }
    }
}
