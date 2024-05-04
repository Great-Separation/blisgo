package blisgo.usecase.request.post;

import blisgo.domain.base.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record UpdatePost(
        @Positive Long postId,
        @NotEmpty String title,
        @NotEmpty String content,
        @Pattern(regexp = Constants.HEX_COLOR_PATTERN) String color,
        @Pattern(regexp = Constants.HTTP_PROTOCOL) String thumbnail,
        String preview) {}
