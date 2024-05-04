package blisgo.domain.dictionary.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WasteId {

    @NotNull
    @Positive
    private Long id;

    public static WasteId of(String string) {
        return new WasteId(Long.parseLong(string));
    }
}
