package blisgo.infrastructure.external.extract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {JsonParser.class, ObjectMapper.class})
class JsonParserTest {

    @Autowired
    private JsonParser jsonParser;

    @Nested
    @DisplayName("parseFirstParagraph 메서드는")
    class parseFirstParagraph {

        @Test
        @DisplayName("첫 번째 문단을 반환한다")
        void paragraph_returnFirstParagraph() {
            String content =
                    "{\"time\": 1714541771286, \"blocks\": [{\"id\": \"-ygVB9m5zU\", \"data\": {\"text\": \"<b>나에게도"
                        + " </b><mark class=\\\"cdx-marker\\\">봄은</mark> <i>올까요?</i>&nbsp;\uD83C\uDF38\"}, \"type\":"
                        + " \"paragraph\"}], \"version\": \"2.29.1\"}";
            var expected = "<b>나에게도 </b><mark class=\"cdx-marker\">봄은</mark> <i>올까요?</i>&nbsp;\uD83C\uDF38";
            var actual = jsonParser.parseFirstParagraph(content);
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("문단이 없으면 빈 문자열을 반환한다")
        void noParagraph_returnNull() {
            String content = "{\"time\": 1714540579556, \"blocks\": [], \"version\": \"2.29.1\"}";
            var actual = jsonParser.parseFirstParagraph(content);
            assertNull(actual);
        }
    }

    @Nested
    @DisplayName("parseFirstImageUrl 메서드는")
    class parseFirstImageUrl {

        @Test
        @DisplayName("첫 번째 이미지 URL 반환한다")
        void imageUrl_returnFirstImageUrl() {
            String content =
                    "{\"time\": 1714407528646, \"blocks\": [{\"id\": \"NIpalqGhZ2\", \"data\": {\"file\": {\"url\":"
                            + " \"https://example.com/image.jpg\"}, \"caption\": \"\", \"stretched\": false,"
                            + " \"withBorder\": false, \"withBackground\": false}, \"type\": \"image\"}, {\"id\":"
                            + " \"xQkkdDC5a1\", \"data\": {\"text\": \"z\"}, \"type\": \"paragraph\"}], \"version\":"
                            + " \"2.29.1\"}";
            var expected = "https://example.com/image.jpg";
            var actual = jsonParser.parseFirstImageUrl(content);
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("이미지가 없으면 null 반환한다")
        void noImageUrl_returnNull() {
            String content = "{\"time\": 1714540579556, \"blocks\": [], \"version\": \"2.29.1\"}";
            var actual = jsonParser.parseFirstImageUrl(content);
            assertNull(actual);
        }
    }

    @Nested
    @DisplayName("parseFilenames 메서드는")
    class parseFilenames {

        @Test
        @DisplayName("파일 이름 목록을 반환한다")
        void filenames_returnFilenames() {
            String content =
                    "{\"time\": 1714546582660, \"blocks\": [{\"id\": \"FlumCcSqDe\", \"data\": {\"file\": {\"url\":"
                            + " \"https://example.com/file1.pdf\"}, \"title\": \"\"}, \"type\": \"attaches\"}, {\"id\":"
                            + " \"f0uRFGoOdj\", \"data\": {\"file\": {\"url\": \"https://example.com/file2.pdf\"},"
                            + " \"title\": \"\"}, \"type\": \"attaches\"}], \"version\": \"2.29.1\"}";
            var expected = List.of(Paths.get("file1.pdf"), Paths.get("file2.pdf"));
            var actual = jsonParser.parseFilenames(content);
            assertEquals(expected.size(), actual.size());

            for (int i = 0; i < expected.size(); i++) {
                assertEquals(expected.get(i), actual.get(i));
            }
        }

        @Test
        @DisplayName("파일이 없으면 빈 배열을 반환한다")
        void noFilenames_returnEmptyList() {
            String content = "{\"time\": 1714540579556, \"blocks\": [], \"version\": \"2.29.1\"}";
            var actual = jsonParser.parseFilenames(content);
            assertTrue(actual.isEmpty());
        }
    }

    @Nested
    @DisplayName("getLocalizedList 메서드는")
    class getLocalizedList {

        @Test
        @DisplayName("지역화된 목록을 반환한다")
        void localeAndData_returnLocalizedList() {
            Locale locale = Locale.ENGLISH;
            String data = "{\"en\": [\"Coated paper\", \"Paper\"], \"ko\": [\"코팅 용지\", \"종이\"]}";

            var expected = List.of("Coated paper", "Paper");
            var actual = jsonParser.getLocalizedList(data, locale);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("지역화된 목록이 없으면 빈 배열을 반환한다")
        void noLocalizedList_returnEmptyArray() {
            Locale locale = Locale.JAPANESE;
            String data = "{\"en\": [\"Coated paper\", \"Paper\"], \"ko\": [\"코팅 용지\", \"종이\"]}";

            var expected = List.of();
            var actual = jsonParser.getLocalizedList(data, locale);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("지역화된 목록이 있으나 비어있으면 빈 배열을 반환한다")
        void withNoData_returnEmptyArray() {
            Locale locale = Locale.ENGLISH;
            String data = "{\"en\": []}";

            var expected = List.of();
            var actual = jsonParser.getLocalizedList(data, locale);

            assertEquals(expected, actual);
        }
    }
}
