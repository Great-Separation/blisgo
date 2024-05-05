package blisgo.infrastructure.internal.ui.view;

import blisgo.infrastructure.internal.ui.base.Router;
import java.util.Map;
import java.util.random.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityView extends Router {

    @GetMapping
    public ModelAndView board() {
        return new ModelAndView(routes(Folder.COMMUNITY, Page.BOARD));
    }

    @GetMapping("/{postId}")
    public ModelAndView content(@PathVariable Long postId) {
        return new ModelAndView(routes(Folder.COMMUNITY, Page.CONTENT), Map.ofEntries(Map.entry("postId", postId)));
    }

    @GetMapping("/write")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView write(@RequestParam(required = false) Long postId, Model model) {
        if (postId != null) {
            model.addAttribute("postId", postId);
        }

        return new ModelAndView(
                routes(Folder.COMMUNITY, Page.WRITE),
                Map.of("color", "#%06x".formatted(RandomGenerator.getDefault().nextInt(0xffffff + 1))));
    }
}
