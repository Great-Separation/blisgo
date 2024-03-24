package blisgo.infrastructure.internal.persistence.base;

import blisgo.infrastructure.internal.persistence.common.JpaPicture;
import blisgo.infrastructure.internal.persistence.common.JpaContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ContentConverter implements AttributeConverter<JpaContent, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String TEXT = "text";
    private static final String BLOCKS = "blocks";
    private static final String URL = "url";
    private static final String IMAGE = "image";
    private static final String PARAGRAPH = "paragraph";

    @Override
    public String convertToDatabaseColumn(JpaContent content) {
        return content.text();
    }

    @Override
    public JpaContent convertToEntityAttribute(String dbData) {
        JsonNode json;
        String thumbnail, preview;

        dbData = dbData == null ? "" : dbData;

        try {
            json = objectMapper.readTree(dbData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        thumbnail = parseFirstImageUrl(json);
        preview = parseFirstParagraph(json);

        return JpaContent.of(dbData, JpaPicture.of(thumbnail), preview);
    }

    private String parseFirstParagraph(JsonNode json) {
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

    private String parseFirstImageUrl(JsonNode json) {
        JsonNode blocksNode = json.get(BLOCKS);

        if (blocksNode == null) {
            return null;
        }

        for (JsonNode blockNode : blocksNode) {
            if (IMAGE.equals(blockNode.get(TYPE).asText())) {
                return blockNode.get(DATA).get(URL).asText();
            }
        }

        return null;
    }


}