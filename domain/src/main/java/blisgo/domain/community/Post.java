package blisgo.domain.community;

import blisgo.domain.common.Author;
import blisgo.domain.common.Content;
import blisgo.domain.community.vo.PostId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    private PostId postId;
    private String title;
    private Author author;
    private Content content;
    private String color;
    private long views;
    private long likes;
    private long replies;
    private OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;

    public static Post create(Long postId, String title, Content content, String color) {
        return create(title, content, color).toBuilder()
                .postId(PostId.of(postId))
                .build();
    }

    public static Post create(String title, Content content, String color) {
        return Post.builder()
                .title(title)
                .content(content)
                .color(color)
                .build();
    }

    public boolean isAuthor(String email) {
        return this.author.email().equals(email);
    }
}
