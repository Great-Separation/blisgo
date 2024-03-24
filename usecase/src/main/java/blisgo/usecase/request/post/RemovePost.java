package blisgo.usecase.request.post;

import lombok.Builder;

@Builder
public record RemovePost(
        Long postId
)  {
}
