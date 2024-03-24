package blisgo.usecase.request.reply;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record GetReply(
        Long postId,
        Long lastReplyId,
        Pageable pageable
) {
}
