package blisgo.infrastructure.internal.ui.render;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import blisgo.domain.member.Member;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.member.mapper.MemberMapper;
import blisgo.infrastructure.internal.security.CustomOidcUserService;
import blisgo.infrastructure.internal.ui.base.UIToast;
import blisgo.usecase.request.member.MemberQuery;
import com.navercorp.fixturemonkey.FixtureMonkey;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
@ContextConfiguration(classes = {MemberRender.class, JacksonConfig.class, MapperConfig.class})
@MockBeans({@MockBean(JpaMetamodelMappingContext.class), @MockBean(CustomOidcUserService.class)})
@SpyBeans({@SpyBean(UIToast.class), @SpyBean(MemberMapper.class)})
class MemberRenderTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create();

    @MockBean
    MemberQuery queryUsecase;

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
    @DisplayName("GET /members")
    class getProfile {

        Member member = fixtureMonkey.giveMeOne(Member.class);

        @Test
        @DisplayName("인증된 사용자는 프로필을 조회할 수 있다.")
        void authenticated_ok() throws Exception {
            var request = MockMvcRequestBuilders.get("/members").with(oidcLogin());

            given(queryUsecase.getMember(any())).willReturn(member);

            mvc.perform(request).andDo(print()).andExpect(status().isOk());
        }

        @Test
        @DisplayName("인증되지 않은 사용자는 프로필을 조회할 수 없다.")
        void anonymous_unauthorized() throws Exception {
            var request = MockMvcRequestBuilders.get("/members");

            given(queryUsecase.getMember(any())).willReturn(null);

            mvc.perform(request).andDo(print()).andExpect(status().isUnauthorized());
        }
    }
}
