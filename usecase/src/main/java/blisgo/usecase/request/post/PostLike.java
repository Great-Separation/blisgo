package blisgo.usecase.request.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record PostLike(@Positive Long postId, @NotNull Boolean isLike) {}
