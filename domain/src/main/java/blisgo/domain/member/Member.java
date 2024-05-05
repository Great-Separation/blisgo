package blisgo.domain.member;

import blisgo.domain.common.Picture;
import blisgo.domain.member.validation.GetMemberValid;
import blisgo.domain.member.vo.MemberId;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @NotNull(groups = {GetMemberValid.class})
    private MemberId memberId;

    @NotEmpty(groups = {GetMemberValid.class})
    private String name;

    @Email
    @NotEmpty(groups = {GetMemberValid.class})
    private String email;

    @NotNull(groups = {GetMemberValid.class})
    private Picture picture;

    @NotNull(groups = {GetMemberValid.class})
    @PastOrPresent
    private OffsetDateTime createdDate;

    @NotNull(groups = {GetMemberValid.class})
    @PastOrPresent
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
