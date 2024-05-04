package blisgo.infrastructure.internal.ui.base;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

@Component
@RequiredArgsConstructor
public class UIToast {

    public static final String STATUS = "STATUS";

    public static final String MSG = "MSG";

    private final MessageSource messageSource;

    private final LocaleResolver localeResolver;

    public HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new IllegalStateException("No request attributes present");
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public String getMessage(final String code, final Object... args) {
        return messageSource.getMessage(code, args, code, localeResolver.resolveLocale(getRequest()));
    }

    public Map<String, Object> popup(ToastStatus status, final String i18nMessageProperty, final Object... args) {
        return Map.ofEntries(Map.entry(STATUS, status.status()), Map.entry(MSG, getMessage(i18nMessageProperty, args)));
    }
}
