package blisgo.usecase.request.reply;

import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record RemoveReply(@Positive Long replyId) {}
