package blisgo.domain.dictionary.vo;

import blisgo.domain.member.vo.MemberId;
import lombok.*;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
public class DogamId {
    private MemberId memberId;
    private WasteId wasteId;
}