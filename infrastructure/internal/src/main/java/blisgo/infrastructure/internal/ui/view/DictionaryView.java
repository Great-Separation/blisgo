package blisgo.infrastructure.internal.ui.view;

import blisgo.infrastructure.internal.ui.base.Router;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/dictionary")
@RequiredArgsConstructor
public class DictionaryView extends Router {
    @GetMapping
    public String dictionary() {
        return routes(Folder.DICTIONARY, Page.CATALOGUE);
    }

    @GetMapping("/{wasteId}")
    public ModelAndView product(@PathVariable Long wasteId) {
        return new ModelAndView(
                routes(Folder.DICTIONARY, Page.INFO),
                Map.of("wasteId", wasteId)
        );
    }
}
