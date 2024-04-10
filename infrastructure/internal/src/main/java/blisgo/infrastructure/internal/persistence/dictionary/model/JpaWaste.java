package blisgo.infrastructure.internal.persistence.dictionary.model;

import blisgo.domain.dictionary.vo.Category;
import blisgo.infrastructure.internal.persistence.base.I18nConverter;
import blisgo.infrastructure.internal.persistence.common.BaseTimeEntity;
import blisgo.infrastructure.internal.persistence.common.JpaPicture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "waste")
@Comment("폐기물")
public class JpaWaste extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("폐기물 Id")
    private Long wasteId;

    @Column(columnDefinition = "json")
    @Convert(converter = I18nConverter.class)
    @Comment("이름")
    private String name;

    @Column(columnDefinition = "json")
    @Convert(converter = I18nConverter.class)
    @Comment("유형")
    private String type;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "picture"))
    private JpaPicture picture;

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "json")
    @Convert(converter = I18nConverter.class)
    @Comment("처리 안내")
    private String treatment;

    @Range(min = 1, max = 10)
    @ColumnDefault("5")
    @Comment("인지도")
    private Short popularity;

    @Comment("조회 수")
    @ColumnDefault("0")
    private Long views;

    @ElementCollection
    @CollectionTable(name = "waste_categories", joinColumns = @JoinColumn(name = "waste_id"))
    @Enumerated(EnumType.STRING)
    @Comment("폐기 분류")
    private final List<Category> categories = new ArrayList<>();
}
