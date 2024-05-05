package blisgo.domain.community;

import blisgo.domain.base.Constants;
import blisgo.domain.common.Author;
import blisgo.domain.common.Content;
import blisgo.domain.community.validation.GetPostValid;
import blisgo.domain.community.validation.GetPostsValid;
import blisgo.domain.community.vo.PostId;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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
public class Post {

    @NotNull(groups = {GetPostValid.class, GetPostsValid.class})
    private PostId postId;

    @NotEmpty(groups = {GetPostValid.class, GetPostsValid.class})
    private String title;

    @NotNull(groups = {GetPostValid.class, GetPostsValid.class})
    private Author author;

    @NotNull(groups = {GetPostValid.class, GetPostsValid.class})
    private Content content;

    @NotEmpty(groups = {GetPostValid.class, GetPostsValid.class})
    @Pattern(regexp = Constants.HEX_COLOR_PATTERN)
    private String color;

    @PositiveOrZero
    private long views;

    @PositiveOrZero
    private long likes;

    @PositiveOrZero
    private long replies;

    @NotNull(groups = {GetPostValid.class, GetPostsValid.class})
    @PastOrPresent
    private OffsetDateTime createdDate;

    @NotNull(groups = {GetPostValid.class, GetPostsValid.class})
    @PastOrPresent
    private OffsetDateTime modifiedDate;

    public static Post create(Long postId, String title, Content content, String color) {
        return create(title, content, color).toBuilder()
                .postId(PostId.of(postId))
                .build();
    }

    public static Post create(String title, Content content, String color) {
        return Post.builder().title(title).content(content).color(color).build();
    }

    public boolean isAuthor(String email) {
        return this.author.email().equals(email);
    }
}
