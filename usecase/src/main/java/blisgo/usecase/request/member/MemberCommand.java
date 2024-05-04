package blisgo.usecase.request.member;

import org.jmolecules.architecture.hexagonal.PrimaryPort;

@PrimaryPort
public interface MemberCommand {

    boolean addMember(AddMember command);

    boolean updateMember(UpdateMember command);

    boolean removeMember(RemoveMember command);
}
