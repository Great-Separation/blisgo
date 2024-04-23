package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.common.Author;
import blisgo.domain.common.Content;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDTO {
    private Long postId;
    private Author author;
    private String title;
    private Content content;
    private String color;
    private Long views;
    private Long likes;
    private Long replies;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
