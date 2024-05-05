package blisgo.usecase.request.post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record RemovePost(@NotNull @Positive Long postId) {}
