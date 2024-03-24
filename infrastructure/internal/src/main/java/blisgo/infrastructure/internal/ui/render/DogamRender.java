package blisgo.infrastructure.internal.ui.render;

import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.usecase.request.dogam.AddDogam;
import blisgo.usecase.request.dogam.DogamCommand;
import blisgo.usecase.request.dogam.DogamQuery;
import blisgo.usecase.request.dogam.GetDogam;
import blisgo.usecase.request.waste.WasteQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequestMapping("/dogams")
@RequiredArgsConstructor
public class DogamRender extends Router {
    private final DogamQuery queryUsecase;
    private final DogamCommand commandUsecase;
    private final WasteQuery wasteQuery;
    private final WasteMapper wasteMapper;

    // 도감 추가
    @PostMapping
    public void createDogam(AddDogam command) {
        commandUsecase.addDogam(command);
    }

    // 도감 삭제

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView dogam(
            @AuthenticationPrincipal DefaultOidcUser oidcUser,
            @PageableDefault(size = 12, sort = "createdDate", direction = DESC) Pageable pageable,
            @RequestParam(required = false) LocalDateTime lastDogamCreatedDate
    ) {
        if (lastDogamCreatedDate == null) {
            lastDogamCreatedDate = LocalDateTime.now();
        }

        GetDogam query = GetDogam.builder()
                .email(oidcUser.getEmail())
                .pageable(pageable)
                .lastDogamCreatedDate(lastDogamCreatedDate)
                .build();

        var wastes = wasteQuery.getWastesFromDogam(query);

        return new ModelAndView(
                routes(Folder.MEMBER, Page.PROFILE) + fragment(Fragment.WASTES),
                Map.of("wastes", wastes.map(wasteMapper::toDTO))
        );

    }
}
