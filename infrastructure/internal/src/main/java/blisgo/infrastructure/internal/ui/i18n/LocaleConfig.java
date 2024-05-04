package blisgo.infrastructure.internal.ui.i18n;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    @Description("클라이언트 요청의 Accept-Language 헤더를 기반으로 Locale을 결정. 값이 없으면 시스템 언어(영어)로 설정")
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        Locale.setDefault(Locale.ENGLISH);

        return localeResolver;
    }
}
