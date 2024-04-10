package blisgo.infrastructure.internal.persistence.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.context.i18n.LocaleContextHolder;

@Converter
public class I18nConverter implements AttributeConverter<String, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(dbData);
            String locale = LocaleContextHolder.getLocale().getLanguage();
            if (jsonNode.has(locale)) {
                return jsonNode.get(locale).asText();
            } else {
                return jsonNode.get("en").asText();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
