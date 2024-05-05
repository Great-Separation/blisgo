package blisgo.infrastructure.internal.ui.render;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import blisgo.domain.dictionary.Waste;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.security.CustomOidcUserService;
import blisgo.infrastructure.internal.ui.base.UIToast;
import blisgo.usecase.request.dogam.AddDogam;
import blisgo.usecase.request.dogam.DogamCommand;
import blisgo.usecase.request.dogam.RemoveDogam;
import blisgo.usecase.request.waste.WasteQuery;
import com.navercorp.fixturemonkey.FixtureMonkey;
import java.time.OffsetDateTime;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
@ContextConfiguration(classes = {DogamRender.class, JacksonConfig.class, MapperConfig.class})
@MockBeans({@MockBean(JpaMetamodelMappingContext.class), @MockBean(CustomOidcUserService.class)})
@SpyBeans({@SpyBean(UIToast.class), @SpyBean(WasteMapper.class)})
class DogamRenderTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    @MockBean
    DogamCommand dogamCommand;

    @MockBean
    WasteQuery wasteQuery;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(print())
                .apply(springSecurity())
                .build();

        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("DELETE /dogams")
    class deleteDogam {
        RemoveDogam command = fixtureMonkey.giveMeOne(RemoveDogam.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/dogams")
                .with(oidcLogin())
                .with(csrf())
                .requestAttr("command", command);

        @Test
        @DisplayName("성공시 200 반환")
        void success_ok() throws Exception {
            given(dogamCommand.removeDogam(any())).willReturn(true);

            mvc.perform(request).andExpect(status().isOk());
        }

        @Test
        @DisplayName("실패시 200 반환")
        void fail_ok() throws Exception {
            given(dogamCommand.removeDogam(any())).willReturn(false);

            mvc.perform(request).andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("POST /dogams")
    class createDogam {
        AddDogam command = fixtureMonkey.giveMeOne(AddDogam.class);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/dogams")
                .with(oidcLogin())
                .with(csrf())
                .requestAttr("command", command);

        @Test
        @DisplayName("성공시 200 반환")
        void success_ok() throws Exception {
            given(dogamCommand.addDogam(any())).willReturn(true);

            mvc.perform(request).andExpect(status().isOk());
        }

        @Test
        @DisplayName("실패시 200 반환")
        void fail_ok() throws Exception {
            given(dogamCommand.addDogam(any())).willReturn(false);

            mvc.perform(request).andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("GET /dogams")
    class getDogams {
        List<Waste> waste = fixtureMonkey.giveMe(Waste.class, randomGenerator.nextInt(0, 101));
        Slice<Waste> slice = new SliceImpl<>(waste);
        OffsetDateTime lastDogamCreatedDate = fixtureMonkey.giveMeOne(OffsetDateTime.class);

        @Test
        @DisplayName("임의값을 넣으면 200 반환")
        void lastDogamCreatedDateAndDogams_ok() throws Exception {
            given(wasteQuery.getWastesFromDogam(any())).willReturn(slice);

            mvc.perform(get("/dogams")
                            .with(oidcLogin())
                            .with(csrf())
                            .param("page", String.valueOf(randomGenerator.nextInt(0, 101)))
                            .param("size", String.valueOf(randomGenerator.nextInt(0, 101)))
                            .param("lastDogamCreatedDate", lastDogamCreatedDate.toString()))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("빈 결과값이어도 200 반환")
        void null_ok() throws Exception {
            given(wasteQuery.getWastesFromDogam(any())).willReturn(new SliceImpl<>(List.of()));

            mvc.perform(get("/dogams").with(oidcLogin()).with(csrf())).andExpect(status().isOk());
        }
    }
}
