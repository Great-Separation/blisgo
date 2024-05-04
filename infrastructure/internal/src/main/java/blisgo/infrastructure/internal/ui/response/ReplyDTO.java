package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.common.Author;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyDTO {

    private Long replyId;

    private Long postId;

    private Author author;

    private String content;

    private OffsetDateTime createdDate;

    private OffsetDateTime modifiedDate;
}
