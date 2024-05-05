package blisgo.usecase.request.waste;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record GetWaste(@NotNull @Positive Long wasteId) {}
