package blisgo.infrastructure.internal.persistence.base;


import blisgo.infrastructure.internal.persistence.common.JpaAuthor;
import blisgo.infrastructure.internal.persistence.common.JpaPicture;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

public class OidcAuditorAware implements AuditorAware<JpaAuthor> {

    @NonNull
    @Override
    public Optional<JpaAuthor> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of(JpaAuthor.of(
                    "admin@blisgo.com",
                    "관리자",
                    JpaPicture.of("https://res.cloudinary.com/blisgo/image/upload/f_auto,q_auto/v1/manifest/admin")
            ));
        }

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        OidcUserInfo oidcUserInfo = oidcUser.getUserInfo();

        return Optional.of(JpaAuthor.of(
                oidcUserInfo.getEmail(),
                oidcUserInfo.getFullName(),
                JpaPicture.of(oidcUserInfo.getPicture())
        ));
    }
}