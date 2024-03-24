package blisgo.usecase.request.dogam;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AddDogam(
        UUID memberId,
        Long wasteId
) {
}
