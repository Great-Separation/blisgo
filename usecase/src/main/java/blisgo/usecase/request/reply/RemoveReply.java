package blisgo.usecase.request.reply;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record RemoveReply(@NotNull @Positive Long replyId) {}
