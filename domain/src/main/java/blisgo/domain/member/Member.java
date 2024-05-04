package blisgo.domain.member;

import blisgo.domain.common.Picture;
import blisgo.domain.member.vo.MemberId;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private MemberId memberId;

    private String name;

    private String email;

    private Picture picture;

    private OffsetDateTime createdDate;

    private OffsetDateTime modifiedDate;

    public static Member create(String name, String email, String picture) {
        return Member.builder()
                .memberId(MemberId.of(email))
                .name(name)
                .email(email)
                .picture(Picture.of(picture))
                .build();
    }
}
