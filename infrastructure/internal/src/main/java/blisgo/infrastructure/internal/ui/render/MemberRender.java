package blisgo.infrastructure.internal.ui.render;

import blisgo.infrastructure.internal.persistence.member.mapper.MemberMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.usecase.request.member.GetMember;
import blisgo.usecase.request.member.MemberQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberRender extends Router {
    private final MemberQuery queryUsecase;
    private final MemberMapper mapper;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(@AuthenticationPrincipal DefaultOidcUser user) {
        var query = GetMember.builder()
                .email(user.getEmail())
                .build();

        var member = queryUsecase.getMember(query);

        return new ModelAndView(
                routes(Router.Folder.MEMBER, Router.Page.PROFILE) + fragment(Router.Fragment.MEMBER),
                Map.of("member", mapper.toDTO(member))
        );
    }
}
