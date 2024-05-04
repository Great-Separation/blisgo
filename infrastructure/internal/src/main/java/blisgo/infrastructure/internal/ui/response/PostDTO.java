package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.common.Author;
import blisgo.domain.common.Content;
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
public class PostDTO {

    private Long postId;

    private Author author;

    private String title;

    private Content content;

    private String color;

    private Long views;

    private Long likes;

    private Long replies;

    private OffsetDateTime createdDate;

    private OffsetDateTime modifiedDate;
}
