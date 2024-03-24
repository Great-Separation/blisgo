package blisgo.usecase.request.dogam;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RemoveDogam(
        UUID memberId,
        Long wasteId
)  {
}
