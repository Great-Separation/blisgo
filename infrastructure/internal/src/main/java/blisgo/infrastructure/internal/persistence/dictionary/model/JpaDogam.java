package blisgo.infrastructure.internal.persistence.dictionary.model;

import blisgo.infrastructure.internal.persistence.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "dogam")
@Comment("도감")
public class JpaDogam extends BaseTimeEntity {
    @EmbeddedId
    private JpaDogamId dogamId;

    @MapsId("wasteId")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "waste_id")
    @Comment("폐기물 번호(PK, FK)")
    private JpaWaste waste;
}
