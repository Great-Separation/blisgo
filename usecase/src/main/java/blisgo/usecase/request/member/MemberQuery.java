package blisgo.usecase.request.member;

import blisgo.domain.member.Member;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

@PrimaryPort
public interface MemberQuery {
    Member getMember(GetMember getMemberQuery);
}
