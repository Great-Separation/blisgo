package blisgo.infrastructure.internal.ui.view;

import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.infrastructure.internal.ui.component.WallpaperChanger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class HomeView extends Router {
    private final WallpaperChanger wallpaperChanger;

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView(
                routes(Page.INDEX),
                Map.of("wallpaper", wallpaperChanger.wallpaperUrl())
        );
    }

    @GetMapping("/login")
    public RedirectView login() {
        return new RedirectView("/oauth2/authorization/okta");
    }
}
