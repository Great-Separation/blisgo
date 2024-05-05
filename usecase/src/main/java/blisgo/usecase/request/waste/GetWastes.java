package blisgo.usecase.request.waste;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder(toBuilder = true)
public record GetWastes(@NotNull Pageable pageable, @NotNull @PositiveOrZero Long lastWasteId) {}
