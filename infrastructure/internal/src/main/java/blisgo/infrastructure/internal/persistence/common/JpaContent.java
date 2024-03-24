package blisgo.infrastructure.internal.persistence.common;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaContent {
    private String text;

    private JpaPicture thumbnail;

    private String preview;
}
