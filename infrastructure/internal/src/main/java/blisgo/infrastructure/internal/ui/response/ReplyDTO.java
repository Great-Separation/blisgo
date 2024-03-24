package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.common.Author;
import blisgo.infrastructure.internal.ui.base.TimeDiffUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyDTO {
    private Long replyId;
    private Long postId;
    private Author author;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String timeDiff;

    public ReplyDTO withTimeDiff() {
        this.timeDiff = TimeDiffUtil.calcTimeDiff(this.createdDate);
        return this;
    }
}

