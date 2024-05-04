package blisgo.domain.dictionary;

import blisgo.domain.dictionary.vo.DogamId;
import blisgo.domain.dictionary.vo.WasteId;
import blisgo.domain.member.vo.MemberId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dogam {

    @NotNull
    private DogamId dogamId;

    @PastOrPresent
    private OffsetDateTime createdDate;

    @PastOrPresent
    private OffsetDateTime modifiedDate;

    public static Dogam create(MemberId memberId, WasteId wasteId) {
        return Dogam.builder().dogamId(DogamId.of(memberId, wasteId)).build();
    }
}
