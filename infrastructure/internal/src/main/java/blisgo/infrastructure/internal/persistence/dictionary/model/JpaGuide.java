package blisgo.infrastructure.internal.persistence.dictionary.model;

import blisgo.domain.dictionary.vo.Category;
import blisgo.infrastructure.internal.persistence.common.JpaPicture;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "guide")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaGuide {
    @Id
    @Enumerated(EnumType.STRING)
    @Comment("카테고리(PK)")
    private Category category;

    @Lob
    @Comment("폐기물 처리 안내")
    private String content;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "picture"))
    private JpaPicture picture;
}
