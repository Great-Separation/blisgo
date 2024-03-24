package blisgo.domain.member.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MemberId {
    private UUID id;

    public static MemberId of(String email) {
        return new MemberId(UUID.nameUUIDFromBytes(email.getBytes()));
    }
}