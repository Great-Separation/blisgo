package blisgo.usecase.request.reply;

import lombok.Builder;

@Builder(toBuilder = true)
public record AddReply(
        Long postId,
        String content
)  {
}
