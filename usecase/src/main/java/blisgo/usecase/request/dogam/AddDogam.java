package blisgo.usecase.request.dogam;

import lombok.Builder;

@Builder(toBuilder = true)
public record AddDogam(
        String email,
        Long wasteId
) {
}
