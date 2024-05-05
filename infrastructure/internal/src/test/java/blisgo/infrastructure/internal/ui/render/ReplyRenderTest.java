package blisgo.infrastructure.internal.ui.render;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import blisgo.domain.community.Reply;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.community.mapper.ReplyMapper;
import blisgo.infrastructure.internal.security.CustomOidcUserService;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.infrastructure.internal.ui.base.UIToast;
import blisgo.usecase.request.reply.ReplyCommand;
import blisgo.usecase.request.reply.ReplyQuery;
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
@ContextConfiguration(classes = {ReplyRender.class, JacksonConfig.class, MapperConfig.class, JsonParser.class})
@MockBeans({@MockBean(JpaMetamodelMappingContext.class), @MockBean(CustomOidcUserService.class)})
@SpyBeans({@SpyBean(UIToast.class), @SpyBean(ReplyMapper.class)})
class ReplyRenderTest extends Router {
    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    @MockBean
    ReplyCommand replyCommand;

    @MockBean
    ReplyQuery replyQuery;

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
    @DisplayName("GET /replies")
    class getReplies {
        List<Reply> replies = fixtureMonkey.giveMe(Reply.class, randomGenerator.nextInt(0, 101));
        Slice<Reply> slice = new SliceImpl<>(replies);
        Long lastReplyId = randomGenerator.nextLong();
        Long postId = randomGenerator.nextLong();

        @Test
        @DisplayName("내용이 정상적으로 입력된 경우 replies 조회 및 CONTENT 페이지로 이동")
        void params_ok() throws Exception {
            var request = MockMvcRequestBuilders.get("/replies")
                    .with(oidcLogin())
                    .param("postId", String.valueOf(postId))
                    .param("lastReplyId", String.valueOf(lastReplyId));

            given(replyQuery.getReplies(any())).willReturn(slice);
            var expected = routes(Folder.COMMUNITY, Page.CONTENT) + fragment(Fragment.REPLIES);

            mvc.perform(request).andExpect(status().isOk()).andExpect(view().name(expected));
        }
    }

    @Nested
    @DisplayName("POST /replies")
    class addReply {
        @Test
        @DisplayName("내용이 정상적으로 입력된 경우 reply 추가")
        void params_ok() throws Exception {
            var request = MockMvcRequestBuilders.post("/replies")
                    .with(oidcLogin())
                    .with(csrf())
                    .param("postId", "1")
                    .param("content", "content");

            mvc.perform(request).andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("DELETE /replies/{replyId}")
    class removeReply {
        @Test
        @DisplayName("내용이 정상적으로 입력된 경우 reply 삭제")
        void params_ok() throws Exception {
            var request = MockMvcRequestBuilders.delete("/replies/1")
                    .with(oidcLogin())
                    .with(csrf());

            mvc.perform(request).andExpect(status().isOk());
        }
    }
}
