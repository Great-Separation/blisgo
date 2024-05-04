package blisgo.infrastructure.external.extract;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WebScrapper.class)
class WebScrapperTest {

    @Autowired
    private WebScrapper webScrapper;

    @Nested
    @DisplayName("scrapPreview 메서드는")
    class scrapPreview {

        @Test
        @DisplayName("정상적인 URL을 입력하면, 프리뷰 데이터를 반환해야 한다")
        void validUrl_returnValue() {
            final String url = "https://naver.com";

            var actual = webScrapper.scrapPreview(url);

            assertNotNull(actual);
        }

        @Test
        @DisplayName("잘못된 URL을 입력하면, 예외를 발생시켜야 한다")
        void givenInvalidUrl_() {
            assertThrows(RuntimeException.class, () -> webScrapper.scrapPreview("invalid_url"));
            assertThrows(RuntimeException.class, () -> webScrapper.scrapPreview("https://notexist.site"));
            assertThrows(RuntimeException.class, () -> webScrapper.scrapPreview("invalid://protocol.url"));
            assertThrows(RuntimeException.class, () -> webScrapper.scrapPreview(null));
        }
    }
}
