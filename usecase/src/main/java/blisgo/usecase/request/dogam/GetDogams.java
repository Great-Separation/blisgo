package blisgo.usecase.request.dogam;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.OffsetDateTime;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder(toBuilder = true)
public record GetDogams(
        @NotEmpty @Email String email,
        @NotNull Pageable pageable,
        @NotNull @PastOrPresent OffsetDateTime lastDogamCreatedDate) {}
