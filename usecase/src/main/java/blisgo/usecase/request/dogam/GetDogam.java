package blisgo.usecase.request.dogam;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Builder
public record GetDogam(
        String email,
        Pageable pageable,
        LocalDateTime lastDogamCreatedDate,
        Long wasteId
) {
}
