package blisgo.infrastructure.internal.ui.view;

import blisgo.infrastructure.internal.ui.base.Router;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberView extends Router {
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile() {
        return routes(Folder.MEMBER, Page.PROFILE);
    }
}
