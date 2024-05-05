package blisgo.usecase.request.dogam;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record RemoveDogam(@NotEmpty @Email String email, @NotNull @Positive Long wasteId) {}
