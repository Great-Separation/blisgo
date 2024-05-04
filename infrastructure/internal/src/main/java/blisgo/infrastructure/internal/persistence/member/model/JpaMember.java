package blisgo.infrastructure.internal.persistence.member.model;

import blisgo.infrastructure.internal.persistence.common.BaseTimeEntity;
import blisgo.infrastructure.internal.persistence.common.JpaPicture;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "member")
@Comment("회원")
public class JpaMember extends BaseTimeEntity {

    @Id
    @Comment("ID")
    private UUID memberId;

    @Column(unique = true)
    @Comment("이름")
    private String name;

    @Comment("이메일")
    private String email;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "picture"))
    private JpaPicture picture;

    public void updateInfo(JpaMember jpaMember) {
        this.name = name == null ? jpaMember.name() : name;
        this.picture = picture == null ? jpaMember.picture() : picture;
    }
}
