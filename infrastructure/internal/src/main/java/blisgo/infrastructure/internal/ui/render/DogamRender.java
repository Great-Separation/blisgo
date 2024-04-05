package blisgo.infrastructure.internal.ui.render;

import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.usecase.request.dogam.*;
import blisgo.usecase.request.waste.WasteQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequestMapping("/dogams")
@RequiredArgsConstructor
public class DogamRender extends Router {
    private final DogamQuery dogamQuery;
    private final DogamCommand dogamCommand;
    private final WasteQuery wasteQuery;
    private final WasteMapper wasteMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public void createDogam(
            @AuthenticationPrincipal DefaultOidcUser oidcUser,
            AddDogam command
    ) {
        command = command.toBuilder()
                .email(oidcUser.getEmail())
                .build();

        dogamCommand.addDogam(command);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void deleteDogam(
            @AuthenticationPrincipal DefaultOidcUser oidcUser,
            RemoveDogam command
    ) {
        command = command.toBuilder()
                .email(oidcUser.getEmail())
                .build();

        dogamCommand.removeDogam(command);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView dogams(
            @AuthenticationPrincipal DefaultOidcUser oidcUser,
            @PageableDefault(size = 12, sort = "createdDate", direction = DESC) Pageable pageable,
            @RequestParam(required = false) LocalDateTime lastDogamCreatedDate
    ) {
        if (lastDogamCreatedDate == null) {
            lastDogamCreatedDate = LocalDateTime.now();
        }

        var query = GetDogam.builder()
                .email(oidcUser.getEmail())
                .pageable(pageable)
                .lastDogamCreatedDate(lastDogamCreatedDate)
                .build();

        var dogams = wasteQuery.getWastesFromDogam(query);

        return new ModelAndView(
                routes(Folder.MEMBER, Page.PROFILE) + fragment(Fragment.DOGAMS),
                Map.of("dogams", dogams.map(wasteMapper::toDTO))
        );
    }

    @GetMapping("/{wasteId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean dogam(
            @AuthenticationPrincipal DefaultOidcUser oidcUser,
            @PathVariable Long wasteId
    ) {
        if (oidcUser == null) {
            return false;
        }

        var query = GetDogam.builder()
                .email(oidcUser.getEmail())
                .wasteId(wasteId)
                .build();

        return dogamQuery.checkThatWasteRegisteredFromDogam(query);
    }
}
