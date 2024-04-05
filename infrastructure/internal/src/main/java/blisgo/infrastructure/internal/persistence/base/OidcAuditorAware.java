package blisgo.infrastructure.internal.persistence.base;


import blisgo.domain.member.vo.MemberId;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;
import java.util.UUID;

public class OidcAuditorAware implements AuditorAware<UUID> {

    @NonNull
    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of(UUID.randomUUID());
        }

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        OidcUserInfo oidcUserInfo = oidcUser.getUserInfo();

        return Optional.ofNullable(MemberId.of(oidcUserInfo.getEmail()).id());
    }
}