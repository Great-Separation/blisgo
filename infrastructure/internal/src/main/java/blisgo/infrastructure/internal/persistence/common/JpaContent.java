package blisgo.infrastructure.internal.persistence.common;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Embeddable
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JpaContent {
    @Comment("글 내용")
    @Column(columnDefinition = "JSON")
    private String text;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "picture"))
    private JpaPicture thumbnail;

    @Comment("글 미리보기")
    private String preview;
}
