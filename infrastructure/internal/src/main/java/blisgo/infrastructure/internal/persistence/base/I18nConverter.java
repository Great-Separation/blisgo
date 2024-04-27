package blisgo.infrastructure.internal.persistence.base;

import blisgo.infrastructure.external.extract.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

@Converter
@RequiredArgsConstructor
public class I18nConverter implements AttributeConverter<String, String> {
    private final JsonParser jsonParser;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return jsonParser.getLocalizedString(dbData, LocaleContextHolder.getLocale());
    }
}
