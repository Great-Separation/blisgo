package blisgo.domain.community;

import blisgo.domain.common.Author;
import blisgo.domain.community.vo.PostId;
import blisgo.domain.community.vo.ReplyId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    private ReplyId replyId;
    private PostId postId;
    private Author author;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
