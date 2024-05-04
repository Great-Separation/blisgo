package blisgo.infrastructure.internal.persistence.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class JpaAuthor {
    private String email;
    private String name;
    private JpaPicture picture;
}
