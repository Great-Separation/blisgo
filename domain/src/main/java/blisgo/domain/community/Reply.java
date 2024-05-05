package blisgo.domain.community;

import blisgo.domain.common.Author;
import blisgo.domain.community.validation.GetRepliesValid;
import blisgo.domain.community.vo.PostId;
import blisgo.domain.community.vo.ReplyId;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotNull(groups = {GetRepliesValid.class})
    private ReplyId replyId;

    @NotNull
    private PostId postId;

    @NotEmpty
    private String content;

    @NotNull(groups = {GetRepliesValid.class})
    private Author author;

    @PastOrPresent
    @NotNull(groups = {GetRepliesValid.class})
    private OffsetDateTime createdDate;

    @PastOrPresent
    @NotNull(groups = {GetRepliesValid.class})
    private OffsetDateTime modifiedDate;
}
