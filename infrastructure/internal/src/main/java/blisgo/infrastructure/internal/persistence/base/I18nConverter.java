package blisgo.infrastructure.internal.persistence.base;

import blisgo.infrastructure.external.extract.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;

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

        Locale locale = List.of(Locale.KOREAN, Locale.ENGLISH)
                .contains(LocaleContextHolder.getLocale()) ?
                LocaleContextHolder.getLocale() :
                Locale.KOREAN;

        return jsonParser.getLocalizedString(dbData, locale);
    }
}
