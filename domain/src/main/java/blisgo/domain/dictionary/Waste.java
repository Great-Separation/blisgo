package blisgo.domain.dictionary;

import blisgo.domain.common.Picture;
import blisgo.domain.dictionary.validation.GetWasteValid;
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

    @NotNull(groups = {GetWasteValid.class})
    private WasteId wasteId;

    @NotEmpty(groups = {GetWasteValid.class})
    private String name;

    @NotEmpty(groups = {GetWasteValid.class})
    private String type;

    @NotNull(groups = {GetWasteValid.class})
    private Picture picture;

    private String treatment;

    @PositiveOrZero
    @NotNull(groups = {GetWasteValid.class})
    private Short popularity;

    @PositiveOrZero
    @NotNull(groups = {GetWasteValid.class})
    private Long views;

    @NotNull(groups = {GetWasteValid.class})
    private List<Category> categories;

    @NotNull(groups = {GetWasteValid.class})
    private List<String> hashtags;

    @PastOrPresent
    @NotNull(groups = {GetWasteValid.class})
    private OffsetDateTime createdDate;

    @PastOrPresent
    @NotNull(groups = {GetWasteValid.class})
    private OffsetDateTime modifiedDate;
}
