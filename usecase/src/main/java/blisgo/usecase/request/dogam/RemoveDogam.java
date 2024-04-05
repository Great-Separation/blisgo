package blisgo.usecase.request.dogam;

import lombok.Builder;

@Builder(toBuilder = true)
public record RemoveDogam(
        String email,
        Long wasteId
) {
}
