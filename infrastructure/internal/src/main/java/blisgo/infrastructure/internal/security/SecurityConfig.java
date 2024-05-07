package blisgo.infrastructure.internal.security;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;

    @Value("${okta.oauth2.issuer}")
    private String issuer;

    @Value("${okta.oauth2.client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.headers(headers -> {
            headers.httpStrictTransportSecurity(
                    hsts -> hsts.includeSubDomains(true).preload(true).maxAgeInSeconds(31536000));
            headers.xssProtection(Customizer.withDefaults());
            headers.contentSecurityPolicy(csp -> csp.policyDirectives(new StringJoiner(";")
                    .add("connect-src 'self' https:")
                    .add("style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net")
                    .add("script-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net")
                    .add("form-action 'self'")
                    .add("object-src 'none'")
                    .add("media-src 'none'")
                    .add("img-src 'self' data: https: http://www.w3.org/2000/svg")
                    .add("font-src 'self'")
                    .add("manifest-src 'self'")
                    .toString()));
        });

        http.oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/oauth2/authorization/okta")
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.oidcUserService(customOidcUserService)));

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .addLogoutHandler(logoutHandler()));

        http.sessionManagement(session -> session.maximumSessions(1).maxSessionsPreventsLogin(true));

        return http.build();
    }

    private LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            try {
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .build()
                        .toUriString();
                response.sendRedirect(issuer + "v2/logout?client_id=" + clientId + "&returnTo=" + baseUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of("http://localhost:8080", "https://blisgo.org", "https://www.blisgo.org"));
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name()));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
