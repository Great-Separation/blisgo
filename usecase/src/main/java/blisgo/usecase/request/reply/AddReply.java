package blisgo.usecase.request.reply;

import lombok.Builder;

@Builder
public record AddReply(
        Long postId,
        String content
)  {
}
