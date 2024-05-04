package blisgo.infrastructure.internal.persistence.dictionary.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class JpaDogamId implements Serializable {

    @Comment("회원 번호(PK, FK)")
    private UUID memberId;

    @Comment("폐기물 번호(PK, FK)")
    private Long wasteId;
}
