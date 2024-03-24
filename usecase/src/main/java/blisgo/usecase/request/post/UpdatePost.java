package blisgo.usecase.request.post;

import lombok.Builder;

@Builder(toBuilder = true)
public record UpdatePost(
        Long postId,
        String title,
        String content
)  {
}
