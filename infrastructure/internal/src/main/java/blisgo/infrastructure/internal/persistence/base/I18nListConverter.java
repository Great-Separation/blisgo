package blisgo.infrastructure.internal.persistence.base;

import blisgo.infrastructure.external.extract.JsonParser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Converter
@RequiredArgsConstructor
public class I18nListConverter implements AttributeConverter<List<String>, String> {
    private final JsonParser jsonContentParser;

    @Override
    public String convertToDatabaseColumn(List<String> attributes) {
        String language = LocaleContextHolder.getLocale().getLanguage();

        if (attributes == null || attributes.isEmpty()) {
            return null;
        }

        return jsonContentParser.toString(Map.of(language, attributes));
    }

    @Override
    public List<String> convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return new ArrayList<>();
        }

        Locale locale = List.of(Locale.KOREAN, Locale.ENGLISH)
                .contains(LocaleContextHolder.getLocale()) ?
                LocaleContextHolder.getLocale() :
                Locale.ENGLISH;

        return jsonContentParser.getLocalizedList(dbData, locale);
    }
}
