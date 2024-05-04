package blisgo.usecase.request.reply;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record AddReply(@Positive Long postId, @NotEmpty String content) {}
