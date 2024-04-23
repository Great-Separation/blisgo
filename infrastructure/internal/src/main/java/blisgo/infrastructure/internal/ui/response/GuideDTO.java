package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.dictionary.vo.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GuideDTO {
    private Category category;
    private String content;
    private String docs;
}
