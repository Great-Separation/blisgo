package blisgo.infrastructure.internal.persistence.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.*;

@Converter
public class I18nListConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attributes) {
        Map<String, List<String>> map = new HashMap<>();
        String locale = LocaleContextHolder.getLocale().getLanguage();

        if (attributes == null || attributes.isEmpty()) {
            return null;
        }
        try {
            map.put(locale, attributes);
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(final String dbData) {
        final List<String> result = new ArrayList<>();

        if (dbData == null) {
            return result;
        }

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(dbData);
            String language = LocaleContextHolder.getLocale().getLanguage();

            jsonNode = switch (language) {
                case "ko", "en" -> jsonNode.get(Locale.of(language).getLanguage());
                default -> jsonNode.get(Locale.ENGLISH.getLanguage());
            };

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (jsonNode != null && jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                result.add(node.asText());
            }
        }

        return result;
    }
}
