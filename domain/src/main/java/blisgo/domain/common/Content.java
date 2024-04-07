package blisgo.domain.common;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {
    private String text;
    private Picture thumbnail;
    private String preview;
}
