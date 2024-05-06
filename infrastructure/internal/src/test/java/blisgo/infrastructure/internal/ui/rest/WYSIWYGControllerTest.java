package blisgo.infrastructure.internal.ui.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.security.CustomOidcUserService;
import blisgo.usecase.port.infra.FileUploadInputPort;
import blisgo.usecase.port.infra.WebScrapInputPort;
import com.navercorp.fixturemonkey.FixtureMonkey;
import java.net.URI;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
@ContextConfiguration(classes = {WYSIWYGController.class})
@MockBeans({@MockBean(CustomOidcUserService.class)})
class WYSIWYGControllerTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create();

    @MockBean
    private FileUploadInputPort fileUploadInputPort;

    @MockBean
    private WebScrapInputPort webScrapInputPort;

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

    @Test
    @DisplayName("파일 업로드")
    void upload() throws Exception {
        URI uri = URI.create("http://example.com");
        given(fileUploadInputPort.upload(any())).willReturn(uri);

        MockMultipartFile file =
                new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        mvc.perform(multipart("/api/editor/upload/file").file(file).with(csrf()).with(oidcLogin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(1))
                .andExpect(jsonPath("$.file.url").value(uri.toString()));
    }

    @Test
    @DisplayName("링크 미리보기")
    void linkPreview() throws Exception {
        String title = fixtureMonkey.giveMeOne(String.class);
        given(webScrapInputPort.scrapPreview(any())).willReturn(Map.of("title", title));

        mvc.perform(get("/api/editor/link-preview")
                        .param("url", "http://example.com")
                        .with(csrf())
                        .with(oidcLogin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title));
    }
}
