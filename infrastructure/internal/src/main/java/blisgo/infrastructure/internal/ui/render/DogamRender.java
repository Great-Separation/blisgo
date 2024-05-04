package blisgo.infrastructure.internal.ui.render;

import static blisgo.infrastructure.internal.ui.base.ToastStatus.ERROR;
import static blisgo.infrastructure.internal.ui.base.ToastStatus.SUCCESS;
import static org.springframework.data.domain.Sort.Direction.DESC;

import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.ui.base.Router;
import blisgo.infrastructure.internal.ui.base.UIToast;
import blisgo.usecase.request.dogam.AddDogam;
import blisgo.usecase.request.dogam.DogamCommand;
import blisgo.usecase.request.dogam.GetDogam;
import blisgo.usecase.request.dogam.RemoveDogam;
import blisgo.usecase.request.waste.WasteQuery;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dogams")
@RequiredArgsConstructor
public class DogamRender extends Router {

    private final DogamCommand dogamCommand;

    private final WasteQuery wasteQuery;

    private final WasteMapper wasteMapper;

    private final UIToast toast;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createDogam(@AuthenticationPrincipal DefaultOidcUser oidcUser, AddDogam command) {
        command = command.toBuilder().email(oidcUser.getEmail()).build();

        return new ModelAndView(
                routesToast(),
                dogamCommand.addDogam(command)
                        ? toast.popup(SUCCESS, "toast.dogam.create.success")
                        : toast.popup(ERROR, "toast.dogam.create.error"));
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteDogam(@AuthenticationPrincipal DefaultOidcUser oidcUser, RemoveDogam command) {
        command = command.toBuilder().email(oidcUser.getEmail()).build();

        return new ModelAndView(
                routesToast(),
                dogamCommand.removeDogam(command)
                        ? toast.popup(SUCCESS, "toast.dogam.delete.success")
                        : toast.popup(ERROR, "toast.dogam.delete.error"));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ModelAndView dogams(
            @AuthenticationPrincipal DefaultOidcUser oidcUser,
            @PageableDefault(size = 12, sort = "createdDate", direction = DESC) Pageable pageable,
            @RequestParam(required = false) OffsetDateTime lastDogamCreatedDate) {
        if (lastDogamCreatedDate == null) {
            lastDogamCreatedDate = OffsetDateTime.now();
        }

        var query = GetDogam.builder()
                .email(oidcUser.getEmail())
                .pageable(pageable)
                .lastDogamCreatedDate(lastDogamCreatedDate)
                .build();

        var dogams = wasteQuery.getWastesFromDogam(query);

        return new ModelAndView(
                routes(Folder.MEMBER, Page.PROFILE) + fragment(Fragment.DOGAMS),
                Map.of("dogams", dogams.map(wasteMapper::toDTO)));
    }
}
