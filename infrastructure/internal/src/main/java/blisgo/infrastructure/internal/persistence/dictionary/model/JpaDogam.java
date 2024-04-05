package blisgo.infrastructure.internal.persistence.dictionary.model;

import blisgo.infrastructure.internal.persistence.common.BaseTimeEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
