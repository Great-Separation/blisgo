package blisgo.usecase.request.post;

import lombok.Builder;

@Builder
public record PostLike(
        Long postId,
        Boolean isLike
) {
}
