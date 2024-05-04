package blisgo.usecase.port.domain;

import blisgo.domain.member.Member;
import java.util.Map;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

@SecondaryPort
public interface MemberOutputPort {

    boolean update(Member domain);

    boolean delete(String email);

    boolean create(Member domain);

    Member read(Map<String, ?> columns);
}
