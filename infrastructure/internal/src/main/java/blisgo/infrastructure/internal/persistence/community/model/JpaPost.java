package blisgo.infrastructure.internal.persistence.community.model;

import blisgo.infrastructure.internal.persistence.base.ContentConverter;
import blisgo.infrastructure.internal.persistence.common.BaseEntity;
import blisgo.infrastructure.internal.persistence.common.JpaContent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "post")
@Comment("게시글")
public class JpaPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("게시글 postId")
    private Long postId;

    @Comment("제목")
    private String title;

    @Comment("내용")
    @Column(name = "content", columnDefinition = "JSON")
    @Convert(converter = ContentConverter.class)
    private JpaContent content;

    @ColumnDefault("0")
    @Comment("조회수")
    private long views;

    @ColumnDefault("0")
    @Comment("좋아요")
    private long likes;

    @Transient
    private long replies;

    @Version
    @Comment("좋아요 동시성 해결을 위한 낙관적 락")
    private Long version;

    public void updateInfo(JpaPost entity) {
        this.title = entity.title;
        this.content = entity.content;
    }
}
