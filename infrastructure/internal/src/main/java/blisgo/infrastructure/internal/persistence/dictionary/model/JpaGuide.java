package blisgo.infrastructure.internal.persistence.dictionary.model;

import blisgo.domain.dictionary.vo.Category;
import blisgo.infrastructure.internal.persistence.base.I18nConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@Table(name = "guide")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class JpaGuide {

    @Id
    @Enumerated(EnumType.STRING)
    @Comment("카테고리(PK)")
    private Category category;

    @Column(columnDefinition = "json")
    @Convert(converter = I18nConverter.class)
    @Comment("폐기물 처리 안내")
    private String content;

    @Column(columnDefinition = "json")
    @Convert(converter = I18nConverter.class)
    @Comment("가이드문서")
    private String docs;
}
