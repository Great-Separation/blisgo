package blisgo.usecase.request.dogam;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;

@Builder
public record GetDogam(
        String email,
        Pageable pageable,
        OffsetDateTime lastDogamCreatedDate,
        Long wasteId
) {
}
