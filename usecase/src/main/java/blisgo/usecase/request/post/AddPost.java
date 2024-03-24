package blisgo.usecase.request.post;

import lombok.Builder;

@Builder
public record AddPost(
        String title,
        String content
) {
}
