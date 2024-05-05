package blisgo.infrastructure.internal.ui.view;

import static org.junit.jupiter.api.Assertions.*;
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

@WebMvcTest(DictionaryView.class)
@ContextConfiguration(classes = {DictionaryView.class})
class DictionaryViewTest extends Router {

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
    @DisplayName("dictionary 페이지 호출")
    void dictionary() throws Exception {
        mvc.perform(get("/dictionary"))
                .andExpect(status().isOk())
                .andExpect(view().name(routes(Folder.DICTIONARY, Page.CATALOGUE)));
    }

    @Test
    @DisplayName("dictionary product 페이지 호출")
    void product() throws Exception {
        var wasteId = randomGenerator.nextLong(1, Long.MAX_VALUE);

        mvc.perform(get("/dictionary/" + wasteId))
                .andExpect(status().isOk())
                .andExpect(view().name(routes(Folder.DICTIONARY, Page.INFO)))
                .andExpect(model().attribute("wasteId", wasteId));
    }
}
