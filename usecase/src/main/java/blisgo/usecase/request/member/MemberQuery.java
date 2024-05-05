package blisgo.usecase.request.member;

import blisgo.domain.member.Member;
import blisgo.domain.member.validation.GetMemberValid;
import jakarta.validation.Valid;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.validation.annotation.Validated;

@Validated
@PrimaryPort
public interface MemberQuery {

    @Valid
    @Validated(GetMemberValid.class)
    Member getMember(@Valid GetMember query);
}
