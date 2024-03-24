package blisgo.domain.community;

import blisgo.domain.common.Author;
import blisgo.domain.common.Content;
import blisgo.domain.community.vo.PostId;
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
public class Post {
    private PostId postId;
    private String title;
    private Author author;
    private Content content;
    private long views;
    private long likes;
    private long replies;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static Post create(Long postId, String title, String content) {
        return Post.builder()
                .postId(PostId.of(postId))
                .title(title)
                .content(Content.of(content))
                .build();
    }

    public static Post create(String title, String content) {
        return Post.builder()
                .title(title)
                .content(Content.of(content))
                .build();
    }

    public void likePost() {
        this.likes++;
    }

    public boolean isAuthor(String email) {
        return this.author.email().equals(email);
    }
}
