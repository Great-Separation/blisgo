package blisgo.domain.member.vo;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MemberId {

    @NotNull
    private UUID id;

    public static MemberId of(String email) {
        return new MemberId(UUID.nameUUIDFromBytes(email.getBytes()));
    }
}
