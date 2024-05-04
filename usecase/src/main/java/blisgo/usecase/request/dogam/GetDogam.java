package blisgo.usecase.request.dogam;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.OffsetDateTime;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record GetDogam(
        @Email String email,
        Pageable pageable,
        @PastOrPresent OffsetDateTime lastDogamCreatedDate,
        @Positive Long wasteId) {}
