package blisgo.usecase.request.reply;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record GetReply(@Positive Long postId, @Positive Long lastReplyId, Pageable pageable) {}
