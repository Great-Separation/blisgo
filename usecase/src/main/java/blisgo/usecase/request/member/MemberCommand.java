package blisgo.usecase.request.member;

import jakarta.validation.Valid;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.validation.annotation.Validated;

@Validated
@PrimaryPort
public interface MemberCommand {

    boolean addMember(@Valid AddMember command);

    boolean updateMember(@Valid UpdateMember command);

    boolean removeMember(@Valid RemoveMember command);
}
