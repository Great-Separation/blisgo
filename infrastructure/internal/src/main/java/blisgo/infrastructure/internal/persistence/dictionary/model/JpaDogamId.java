package blisgo.infrastructure.internal.persistence.dictionary.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaDogamId implements Serializable {
    @Comment("회원 번호(PK, FK)")
    private UUID memberId;

    @Comment("폐기물 번호(PK, FK)")
    private Long wasteId;
}