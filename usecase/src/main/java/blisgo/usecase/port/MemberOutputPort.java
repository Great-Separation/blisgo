package blisgo.usecase.port;

import blisgo.domain.member.Member;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

import java.util.Map;

@SecondaryPort
public interface MemberOutputPort {
    boolean update(Member domain);

    boolean delete(String email);

    boolean create(Member domain);

    Member read(Map<String, ?> columns);
}
