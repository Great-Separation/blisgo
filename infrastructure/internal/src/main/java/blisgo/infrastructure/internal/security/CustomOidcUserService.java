package blisgo.infrastructure.internal.security;

import blisgo.domain.member.Member;
import blisgo.usecase.port.MemberInputPort;
import blisgo.usecase.request.member.GetMember;
import blisgo.usecase.request.member.UpdateMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private final MemberInputPort usecase;

    @Override
    public OidcUser loadUser(OidcUserRequest oidcUserRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(oidcUserRequest);

        registerOrUpdate(oidcUser.getUserInfo());

        GetMember query = GetMember.builder()
                .email(oidcUser.getUserInfo().getEmail())
                .build();

        Member member = usecase.getMember(query);

        return new DefaultOidcUser(
                oidcUser.getAuthorities(),
                oidcUserRequest.getIdToken(),
                updateUserInfo(oidcUser.getUserInfo(), member)
        );
    }

    private OidcUserInfo updateUserInfo(OidcUserInfo info, Member member) {
        Map<String, Object> claims = new HashMap<>(info.getClaims());
        claims.put("email", member.email());
        claims.put("nickname", member.name());
        claims.put("picture", member.picture().url());

        return new OidcUserInfo(claims);
    }

    private void registerOrUpdate(OidcUserInfo oidcUserInfo) {
        String email = oidcUserInfo.getEmail();
        String name = oidcUserInfo.getNickName();
        String picture = oidcUserInfo.getPicture();

        UpdateMember command = UpdateMember.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .build();

        usecase.updateMember(command);
    }
}
