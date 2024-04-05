package blisgo.domain.dictionary.vo;

import blisgo.domain.member.vo.MemberId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DogamId {
    private MemberId memberId;
    private WasteId wasteId;
}