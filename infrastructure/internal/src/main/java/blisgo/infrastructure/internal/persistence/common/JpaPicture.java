package blisgo.infrastructure.internal.persistence.common;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.URL;

@Builder
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JpaPicture {
    @URL(protocol = "https")
    @Comment("이미지")
    private String url;
}