package blisgo.infrastructure.external.extract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class JsonParser {
    private final ObjectMapper objectMapper;

    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String TEXT = "text";
    private static final String BLOCKS = "blocks";
    private static final String URL = "url";
    private static final String IMAGE = "image";
    private static final String ATTACHES = "attaches";
    private static final String PARAGRAPH = "paragraph";
    private static final String FILE = "file";

    public String toString(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode toJson(final String data) {
        try {
            return objectMapper.readTree(data);
        } catch (JsonProcessingException e) {
            return JsonNodeFactory.instance.objectNode();
        }
    }

    public String parseFirstParagraph(final String data) {
        final JsonNode json = toJson(data);
        final JsonNode blocksNode = json.path(BLOCKS);

        for (JsonNode blockNode : blocksNode) {
            if (PARAGRAPH.equals(blockNode.path(TYPE).asText())) {
                return blockNode.path(DATA).path(TEXT).asText();
            }
        }

        return null;
    }

    public String parseFirstImageUrl(final String data) {
        final JsonNode json = toJson(data);
        final JsonNode blocksNode = json.path(BLOCKS);

        for (JsonNode blockNode : blocksNode) {
            if (IMAGE.equals(blockNode.path(TYPE).asText())) {
                return blockNode.path(DATA).path(FILE).path(URL).asText();
            }
        }

        return null;
    }

    public List<Path> parseFilenames(final String data) {
        final List<Path> paths = new ArrayList<>();
        final JsonNode json = toJson(data);
        final JsonNode blocksNode = json.path(BLOCKS);

        for (JsonNode blockNode : blocksNode) {
            if (List.of(IMAGE, ATTACHES).contains(blockNode.path(TYPE).asText())) {
                String url = blockNode.path(DATA).path(FILE).path(URL).asText();
                String path = url.substring(url.lastIndexOf("/") + 1);

                paths.add(Paths.get(path));
            }
        }

        return List.copyOf(paths);
    }

    public List<String> getLocalizedList(final String data, final Locale locale) {
        final List<String> result = new ArrayList<>();
        final JsonNode json = toJson(data);
        final JsonNode localizedJson = json.path(locale.getLanguage());

        localizedJson.forEach(node -> result.add(node.asText()));

        return List.copyOf(result);
    }

    public String getLocalizedString(final String data, final Locale locale) {
        final JsonNode json = toJson(data);
        return json.path(locale.getLanguage())
                .asText(json.path(Locale.KOREAN.getLanguage()).asText());
    }
}