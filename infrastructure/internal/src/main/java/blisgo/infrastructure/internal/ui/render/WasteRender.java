package blisgo.infrastructure.internal.ui.render;

import static org.springframework.data.domain.Sort.Direction.ASC;

import blisgo.infrastructure.internal.persistence.dictionary.mapper.GuideMapper;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.usecase.request.dogam.DogamQuery;
import blisgo.usecase.request.dogam.GetDogam;
import blisgo.usecase.request.waste.GetWaste;
import blisgo.usecase.request.waste.GetWastes;
import blisgo.usecase.request.waste.WasteQuery;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/wastes")
@RequiredArgsConstructor
public class WasteRender extends Router {

    private final WasteQuery wasteQuery;

    private final WasteMapper wasteMapper;

    private final GuideMapper guideMapper;

    private final DogamQuery dogamQuery;

    @GetMapping
    public ModelAndView getWastes(
            @PageableDefault(size = 24, sort = "wasteId", direction = ASC) Pageable pageable,
            @RequestParam(required = false, defaultValue = "0") Long lastWasteId) {
        var query =
                GetWastes.builder().pageable(pageable).lastWasteId(lastWasteId).build();

        var wastes = wasteQuery.getWastes(query);

        return new ModelAndView(
                routes(Folder.DICTIONARY, Page.CATALOGUE) + fragment(Fragment.WASTES),
                Map.of("wastes", wastes.map(wasteMapper::toDTO)));
    }

    @GetMapping("/{wasteId}")
    public ModelAndView getWaste(@PathVariable Long wasteId, @AuthenticationPrincipal DefaultOidcUser oidcUser) {
        var query = GetWaste.builder().wasteId(wasteId).build();

        var waste = wasteQuery.getWaste(query);

        var guides = wasteQuery.getGuides(waste.categories());
        var relatedWastes = wasteQuery.getWastesRelated(waste.categories());

        var map = new HashMap<>(Map.ofEntries(
                Map.entry("waste", wasteMapper.toDTO(waste)),
                Map.entry("guides", guideMapper.toDTOs(guides)),
                Map.entry("relatedWastes", wasteMapper.toDTOs(relatedWastes))));

        if (oidcUser != null) {
            var getDogam = GetDogam.builder()
                    .email(oidcUser.getEmail())
                    .wasteId(wasteId)
                    .build();

            var dogamExists = dogamQuery.checkThatWasteRegisteredFromDogam(getDogam);
            map.put("dogamExists", dogamExists);
        }

        return new ModelAndView(routes(Folder.DICTIONARY, Page.INFO) + fragment(Fragment.WASTE), map);
    }
}
