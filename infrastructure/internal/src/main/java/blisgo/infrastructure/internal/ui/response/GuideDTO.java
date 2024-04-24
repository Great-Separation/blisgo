package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.dictionary.vo.Category;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GuideDTO {
    private Category category;
    private String content;
    private String docs;
}
