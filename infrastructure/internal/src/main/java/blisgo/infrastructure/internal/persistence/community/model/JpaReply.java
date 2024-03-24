package blisgo.infrastructure.internal.persistence.community.model;

import blisgo.infrastructure.internal.persistence.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "reply")
@Comment("댓글")
public class JpaReply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("댓글 Id")
    private Long replyId;

    @Comment("게시글 Id")
    private Long postId;

    @Lob
    @Comment("내용")
    private String content;

    public void updateInfo(JpaReply jpaReply) {
        this.content = jpaReply.content;
    }
}
