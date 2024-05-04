package blisgo.domain.community;

import blisgo.domain.common.Author;
import blisgo.domain.community.vo.PostId;
import blisgo.domain.community.vo.ReplyId;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {

    private ReplyId replyId;

    private PostId postId;

    private String content;

    private Author author;

    private OffsetDateTime createdDate;

    private OffsetDateTime modifiedDate;
}
