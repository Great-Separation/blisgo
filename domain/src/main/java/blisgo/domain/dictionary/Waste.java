package blisgo.domain.dictionary;

import blisgo.domain.common.Picture;
import blisgo.domain.dictionary.vo.Category;
import blisgo.domain.dictionary.vo.WasteId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Waste {
    private WasteId wasteId;
    private String name;
    private String type;
    private Picture picture;
    private String treatment;
    private Short popularity;
    private Long views;
    private List<Category> categories;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}