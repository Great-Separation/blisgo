package blisgo.infrastructure.internal.ui.view;

import blisgo.infrastructure.internal.persistence.community.mapper.PostMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.usecase.request.post.GetPost;
import blisgo.usecase.request.post.PostQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.random.RandomGenerator;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityView extends Router {
    private final PostQuery queryUsecase;
    private final PostMapper mapper;

    @GetMapping
    public ModelAndView board() {
        return new ModelAndView(routes(Folder.COMMUNITY, Page.BOARD));
    }

    @GetMapping("/{postId}")
    public ModelAndView content(@PathVariable Long postId) {
        return new ModelAndView(
                routes(Folder.COMMUNITY, Page.CONTENT),
                Map.ofEntries(
                        Map.entry("postId", postId)
                )
        );
    }

    @GetMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView write(
            @AuthenticationPrincipal DefaultOidcUser oidcUser,
            @RequestParam(required = false) Long postId,
            Model model
    ) {
        if (postId != null) {
            GetPost query = GetPost.builder()
                    .postId(postId)
                    .build();

            var post = queryUsecase.getPost(query);

            if (post != null && post.isAuthor(oidcUser.getEmail())) {
                model.addAttribute("post", mapper.toDTO(post));
            }
        }

        return new ModelAndView(
                routes(Folder.COMMUNITY, Page.WRITE),
                Map.of("color", String.format("#%06x", RandomGenerator.getDefault().nextInt(0xffffff + 1)))
        );
    }
}
