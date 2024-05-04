package blisgo.infrastructure.internal.persistence.base;

import blisgo.infrastructure.external.extract.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

@Converter
@RequiredArgsConstructor
public class I18nListConverter implements AttributeConverter<List<String>, String> {

    private final JsonParser jsonParser;

    @Override
    public String convertToDatabaseColumn(List<String> attributes) {
        Locale locale = List.of(Locale.KOREAN, Locale.ENGLISH).contains(LocaleContextHolder.getLocale())
                ? LocaleContextHolder.getLocale()
                : Locale.ENGLISH;

        if (attributes == null || attributes.isEmpty()) {
            return null;
        }

        return jsonParser.toString(Map.of(locale.getLanguage(), attributes));
    }

    @Override
    public List<String> convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return new ArrayList<>();
        }

        Locale locale = List.of(Locale.KOREAN, Locale.ENGLISH).contains(LocaleContextHolder.getLocale())
                ? LocaleContextHolder.getLocale()
                : Locale.ENGLISH;

        return jsonParser.getLocalizedList(dbData, locale);
    }
}
