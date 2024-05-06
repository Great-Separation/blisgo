package blisgo.infrastructure.internal.ui.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import blisgo.infrastructure.internal.ui.base.Router;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(CommunityView.class)
@ContextConfiguration(classes = {CommunityView.class})
class CommunityViewTest extends Router {

    final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("community board 페이지 호출")
    void board() throws Exception {
        mvc.perform(get("/community").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(routes(Folder.COMMUNITY, Page.BOARD)));
    }

    @Test
    @DisplayName("community content 페이지 호출")
    void content() throws Exception {
        var postId = randomGenerator.nextLong(1, Long.MAX_VALUE);

        mvc.perform(get("/community/" + postId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(routes(Folder.COMMUNITY, Page.CONTENT)))
                .andExpect(model().attribute("postId", postId));
    }

    @Test
    @DisplayName("community write 페이지 호출")
    void write() throws Exception {
        var postId = randomGenerator.nextLong(1, Long.MAX_VALUE);

        mvc.perform(get("/community/write")
                        .param("postId", Long.toString(postId))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(routes(Folder.COMMUNITY, Page.WRITE)))
                .andExpect(model().attribute("postId", postId));
    }
}
