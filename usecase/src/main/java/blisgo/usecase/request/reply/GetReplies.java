package blisgo.usecase.request.reply;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder(toBuilder = true)
public record GetReplies(
        @NotNull @Positive Long postId, @NotNull @PositiveOrZero Long lastReplyId, @NotNull Pageable pageable) {}
