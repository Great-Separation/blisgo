package blisgo.domain.dictionary;

import blisgo.domain.dictionary.vo.DogamId;
import blisgo.domain.dictionary.vo.WasteId;
import blisgo.domain.member.vo.MemberId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dogam {
    private DogamId dogamId;
    private Waste waste;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static Dogam create(MemberId memberId, WasteId wasteId) {
        return Dogam.builder()
                .dogamId(DogamId.of(memberId, wasteId))
                .waste(Waste.builder().wasteId(wasteId).build())
                .createdDate(LocalDateTime.now())
                .build();
    }
}
