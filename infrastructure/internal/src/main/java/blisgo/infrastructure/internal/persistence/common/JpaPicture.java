package blisgo.infrastructure.internal.persistence.common;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.URL;

@Builder
@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaPicture {
    @URL(protocol = "https")
    @Comment("이미지")
    private String url;
}