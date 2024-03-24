package blisgo.infrastructure.internal.persistence.common;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Embeddable
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaAuthor {

    @Column(name = "author_email")
    @Comment("회원 이메일(FK)")
    private String email;

    @Column(name = "author_name")
    @Comment("작성자")
    private String name;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "author_picture"))
    private JpaPicture picture;
}