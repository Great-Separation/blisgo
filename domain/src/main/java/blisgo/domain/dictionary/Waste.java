package blisgo.domain.dictionary;

import blisgo.domain.common.Picture;
import blisgo.domain.dictionary.vo.Category;
import blisgo.domain.dictionary.vo.WasteId;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Waste {

    @NotNull
    private WasteId wasteId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String type;

    @NotNull
    private Picture picture;

    @NotEmpty
    private String treatment;

    @PositiveOrZero
    @NotNull
    private Short popularity;

    @PositiveOrZero
    private Long views;

    @NotEmpty
    private List<Category> categories;

    @NotNull
    private List<String> hashtags;

    @PastOrPresent
    private OffsetDateTime createdDate;

    @PastOrPresent
    private OffsetDateTime modifiedDate;
}
