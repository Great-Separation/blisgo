package blisgo.infrastructure.internal.persistence.dictionary.model;

import blisgo.domain.dictionary.vo.Category;
import blisgo.infrastructure.internal.persistence.base.I18nConverter;
import blisgo.infrastructure.internal.persistence.base.I18nListConverter;
import blisgo.infrastructure.internal.persistence.common.BaseTimeEntity;
import blisgo.infrastructure.internal.persistence.common.JpaPicture;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Basic;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.Range;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "waste")
@Comment("폐기물")
public class JpaWaste extends BaseTimeEntity {

    @ElementCollection
    @CollectionTable(name = "waste_categories", joinColumns = @JoinColumn(name = "waste_id"))
    @Enumerated(EnumType.STRING)
    @Comment("폐기물 분류")
    private final List<Category> categories = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "waste_hashtags", joinColumns = @JoinColumn(name = "waste_id"))
    @Column(columnDefinition = "json")
    @Convert(converter = I18nListConverter.class)
    @Comment("폐기물 해시태그")
    private final List<String> hashtags = new ArrayList<>();

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
}
