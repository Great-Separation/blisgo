package blisgo.usecase.request.post;

import lombok.Builder;

@Builder(toBuilder = true)
public record AddPost(
        String title,
        String content,
        String thumbnail,
        String preview
) {
}
