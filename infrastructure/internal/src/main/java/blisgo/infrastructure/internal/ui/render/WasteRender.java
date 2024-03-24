package blisgo.infrastructure.internal.ui.render;

import blisgo.infrastructure.internal.persistence.dictionary.mapper.GuideMapper;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.usecase.request.waste.GetWaste;
import blisgo.usecase.request.waste.WasteQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Controller
@RequestMapping("/wastes")
@RequiredArgsConstructor
public class WasteRender extends Router {
    private final WasteQuery queryUsecase;
    private final WasteMapper wasteMapper;
    private final GuideMapper guideMapper;

    @GetMapping
    public ModelAndView wastes(
            @PageableDefault(size = 24, sort = "wasteId", direction = ASC) Pageable pageable,
            @RequestParam(required = false, defaultValue = "0") Long lastWasteId,
            @RequestParam(required = false) Long wasteId
    ) {
        if (wasteId == null) {
            GetWaste query = GetWaste.builder()
                    .pageable(pageable)
                    .lastWasteId(lastWasteId)
                    .build();

            var wastes = queryUsecase.getWastes(query);

            return new ModelAndView(
                    routes(Folder.DICTIONARY, Page.CATALOGUE) + fragment(Fragment.WASTES),
                    Map.of("wastes", wastes.map(wasteMapper::toDTO))
            );
        } else {
            var query = GetWaste.builder()
                    .wasteId(wasteId)
                    .build();

            var waste = queryUsecase.getWaste(query);
            var guides = queryUsecase.getGuides(waste.categories());
            var relatedWastes = queryUsecase.getWastesRelated(waste.categories());

            return new ModelAndView(
                    routes(Folder.DICTIONARY, Page.INFO) + fragment(Fragment.WASTE),
                    Map.ofEntries(
                            Map.entry("waste", wasteMapper.toDTO(waste)),
                            Map.entry("guides", guideMapper.toDTOs(guides)),
                            Map.entry("relatedWastes", wasteMapper.toDTOs(relatedWastes))
                    )
            );
        }
    }
}
