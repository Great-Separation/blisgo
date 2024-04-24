package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.common.Picture;
import blisgo.domain.dictionary.vo.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class WasteDTO {
    private Long wasteId;
    private String name;
    private String type;
    private Picture picture;
    private String treatment;
    private Short popularity;
    private Long views;
    private List<Category> categories;
    private List<String> hashtags;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
