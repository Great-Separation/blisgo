package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.common.Picture;
import blisgo.domain.dictionary.vo.Category;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    private OffsetDateTime createdDate;

    private OffsetDateTime modifiedDate;
}
