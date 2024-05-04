package blisgo.usecase.request.dogam;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record AddDogam(@Email String email, @Positive Long wasteId) {}
