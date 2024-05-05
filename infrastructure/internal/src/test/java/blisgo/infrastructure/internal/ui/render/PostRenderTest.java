package blisgo.infrastructure.internal.ui.render;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import blisgo.domain.community.Post;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.community.mapper.PostMapper;
import blisgo.infrastructure.internal.security.CustomOidcUserService;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.infrastructure.internal.ui.base.UIToast;
import blisgo.usecase.request.post.AddPost;
import blisgo.usecase.request.post.PostCommand;
import blisgo.usecase.request.post.PostQuery;
import blisgo.usecase.request.post.UpdatePost;
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
@ContextConfiguration(classes = {PostRender.class, JacksonConfig.class, MapperConfig.class, JsonParser.class})
@MockBeans({@MockBean(JpaMetamodelMappingContext.class), @MockBean(CustomOidcUserService.class)})
@SpyBeans({@SpyBean(UIToast.class), @SpyBean(PostMapper.class)})
class PostRenderTest extends Router {
    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    @MockBean
    PostCommand postCommand;

    @MockBean
    PostQuery postQuery;

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
    @DisplayName("GET /posts")
    class getPosts {
        List<Post> posts = fixtureMonkey.giveMe(Post.class, randomGenerator.nextInt(0, 101));
        Slice<Post> slice = new SliceImpl<>(posts);
        Long lastPostId = randomGenerator.nextLong();

        @Test
        @DisplayName("lastPostId가 null인 경우 posts 조회 및 BOARD 페이지로 이동")
        void lastPostId_ok() throws Exception {
            var request = MockMvcRequestBuilders.get("/posts")
                    .param("lastPostId", lastPostId.toString())
                    .with(oidcLogin());

            given(postQuery.getPosts(any())).willReturn(slice);
            var expected = routes(Folder.COMMUNITY, Page.BOARD) + fragment(Fragment.POSTS);

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name(expected))
                    .andExpect(model().attributeExists(Fragment.POSTS.name().toLowerCase()));
        }
    }

    @Nested
    @DisplayName("GET /posts/{postId}")
    class getPost {
        Post post = fixtureMonkey.giveMeOne(Post.class);

        @Test
        @DisplayName("edit가 false인 경우 post 조회 및 CONTENT 페이지로 이동")
        void editFalse_readPost() throws Exception {
            given(postQuery.getPost(any())).willReturn(post);

            var request = MockMvcRequestBuilders.get("/posts/" + post.postId().id())
                    .with(oidcLogin())
                    .param("edit", "false");

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name(routes(Folder.COMMUNITY, Page.CONTENT) + fragment(Fragment.POST)));
        }

        @Test
        @DisplayName("edit가 true인 경우 post 조회 및 WRITE 페이지로 이동")
        void editTrue_writePost() throws Exception {
            var request = MockMvcRequestBuilders.get("/posts/" + post.postId().id())
                    .with(oidcLogin()
                            .idToken(token -> token.claim("email", post.author().email())))
                    .param("edit", "true");

            given(postQuery.getPost(any())).willReturn(post);

            mvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(view().name(routes(Folder.COMMUNITY, Page.WRITE) + fragment(Fragment.POST)));
        }
    }

    @Nested
    @DisplayName("POST /posts")
    class addPost {
        AddPost command = fixtureMonkey.giveMeOne(AddPost.class);

        @Test
        @DisplayName("내용이 정상적으로 입력된 경우 200 반환")
        void params_ok() throws Exception {

            var request = MockMvcRequestBuilders.post("/posts")
                    .with(oidcLogin())
                    .with(csrf())
                    .param("title", command.title())
                    .param("content", command.content());

            given(postCommand.addPost(any())).willReturn(true);

            mvc.perform(request).andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    @DisplayName("PATCH /posts/{postId}")
    class updatePost {
        UpdatePost command = fixtureMonkey.giveMeOne(UpdatePost.class);

        @Test
        @DisplayName("내용이 정상적으로 입력된 경우 200 반환")
        void params_ok() throws Exception {

            var request = MockMvcRequestBuilders.patch("/posts/" + command.postId())
                    .with(oidcLogin())
                    .with(csrf())
                    .param("title", command.title())
                    .param("content", command.content());

            given(postCommand.updatePost(any())).willReturn(true);

            mvc.perform(request).andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    @DisplayName("DELETE /posts/{postId}")
    class removePost {
        Long postId = randomGenerator.nextLong();

        @Test
        @DisplayName("postId가 정상적으로 입력된 경우 200 반환")
        void params_ok() throws Exception {

            var request = MockMvcRequestBuilders.delete("/posts/" + postId)
                    .with(oidcLogin())
                    .with(csrf());

            given(postCommand.removePost(any())).willReturn(true);

            mvc.perform(request).andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    @DisplayName("POST /posts/{postId}/like")
    class like {
        @Test
        @DisplayName("postId가 정상적으로 입력된 경우 200 반환")
        void like_ok() throws Exception {
            var request = MockMvcRequestBuilders.post("/posts/" + randomGenerator.nextLong() + "/like")
                    .with(oidcLogin())
                    .with(csrf());

            given(postCommand.like(any())).willReturn(true);

            mvc.perform(request).andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("DELETE /posts/{postId}/like")
    class unlike {
        @Test
        @DisplayName("postId가 정상적으로 입력된 경우 200 반환")
        void dislike_ok() throws Exception {
            var request = MockMvcRequestBuilders.post("/posts/" + randomGenerator.nextLong() + "/like")
                    .with(oidcLogin())
                    .with(csrf());

            given(postCommand.like(any())).willReturn(true);

            mvc.perform(request).andExpect(status().isOk());
        }
    }
}
