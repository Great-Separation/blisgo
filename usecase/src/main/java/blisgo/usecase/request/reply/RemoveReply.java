package blisgo.usecase.request.reply;

import lombok.Builder;

@Builder
public record RemoveReply(
        Long replyId
)  {
}
