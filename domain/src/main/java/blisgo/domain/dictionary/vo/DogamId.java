package blisgo.domain.dictionary.vo;

import blisgo.domain.member.vo.MemberId;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DogamId {

    @NotNull
    private MemberId memberId;

    @NotNull
    private WasteId wasteId;
}
