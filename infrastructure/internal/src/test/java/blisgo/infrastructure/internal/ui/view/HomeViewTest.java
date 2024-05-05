package blisgo.infrastructure.internal.ui.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import blisgo.infrastructure.internal.ui.base.Router;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(HomeView.class)
@ContextConfiguration(classes = {HomeView.class})
class HomeViewTest extends Router {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("index 페이지 호출")
    void index() throws Exception {
        mvc.perform(get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name(routes(Page.INDEX)));
    }

    @Test
    @DisplayName("auth0 redirect")
    void login() throws Exception {
        mvc.perform(get("/login")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
