package blisgo.infrastructure.internal.ui.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class ContentParser {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String TEXT = "text";
    private static final String BLOCKS = "blocks";
    private static final String URL = "url";
    private static final String IMAGE = "image";
    private static final String ATTACHES = "attaches";
    private static final String PARAGRAPH = "paragraph";
    private static final String FILE = "file";

    public static JsonNode toJson(String content) {
        JsonNode json;

        String text = Optional.ofNullable(content).orElse("");

        try {
            json = objectMapper.readTree(text);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return json;
    }

    public static String parseFirstParagraph(JsonNode json) {
        JsonNode blocksNode = json.get(BLOCKS);

        if (blocksNode == null) {
            return null;
        }

        for (JsonNode blockNode : blocksNode) {
            if (PARAGRAPH.equals(blockNode.get(TYPE).asText())) {
                return blockNode.get(DATA).get(TEXT).asText();
            }
        }

        return null;
    }

    public static String parseFirstImageUrl(JsonNode json) {
        JsonNode blocksNode = json.get(BLOCKS);

        if (blocksNode == null) {
            return null;
        }

        for (JsonNode blockNode : blocksNode) {
            if (IMAGE.equals(blockNode.get(TYPE).asText())) {
                return blockNode.get(DATA).get(FILE).get(URL).asText();
            }
        }

        return null;
    }

    public static List<Path> parseFilenames(JsonNode json) {
        JsonNode blocksNode = json.get(BLOCKS);
        List<Path> paths = new ArrayList<>();

        if (blocksNode == null) {
            return Collections.emptyList();
        }

        for (JsonNode blockNode : blocksNode) {
            if (List.of(IMAGE, ATTACHES).contains(blockNode.get(TYPE).asText())) {
                String url = blockNode.get(DATA).get(FILE).get(URL).asText();
                String path = url.substring(url.lastIndexOf("/") + 1);

                paths.add(Paths.get(path));
            }
        }

        return paths;
    }


}