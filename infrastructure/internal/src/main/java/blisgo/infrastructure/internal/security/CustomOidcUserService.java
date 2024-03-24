package blisgo.infrastructure.internal.security;

import blisgo.usecase.port.MemberInputPort;
import blisgo.usecase.request.member.UpdateMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private final MemberInputPort usecase;

    @Override
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(oidcUserRequest);

        registerOrUpdate(oidcUser.getUserInfo());

        return oidcUser;
    }

    public void registerOrUpdate(OidcUserInfo oidcUserInfo) {
        String email = oidcUserInfo.getEmail();
        String name = oidcUserInfo.getFullName();
        String picture = oidcUserInfo.getPicture();

        UpdateMember command = UpdateMember.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .build();

        usecase.updateMember(command);
    }
}
