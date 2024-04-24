package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.common.Author;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyDTO {
    private Long replyId;
    private Long postId;
    private Author author;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}

