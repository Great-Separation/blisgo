package blisgo.infrastructure.internal.persistence.base;

import blisgo.domain.member.vo.MemberId;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Description("도메인 중 회원 정보 데이터가 필요한 경우 OIDC 에서 받아온 회원 정보를 사용하도록 함")
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
