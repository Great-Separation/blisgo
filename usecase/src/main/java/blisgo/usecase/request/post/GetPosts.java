package blisgo.usecase.request.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder(toBuilder = true)
public record GetPosts(@NotNull @Positive Long lastPostId, @NotNull Pageable pageable) {}
