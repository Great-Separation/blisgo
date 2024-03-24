package blisgo.usecase.request.post;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record GetPost(
        Long postId,
        Pageable pageable
) {
}
