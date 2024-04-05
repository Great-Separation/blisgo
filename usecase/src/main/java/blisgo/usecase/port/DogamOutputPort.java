package blisgo.usecase.port;

import blisgo.domain.dictionary.Dogam;
import blisgo.domain.dictionary.vo.DogamId;
import blisgo.domain.dictionary.vo.WasteId;
import blisgo.domain.member.vo.MemberId;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

@SecondaryPort
public interface DogamOutputPort {
    boolean delete(DogamId identifier);

    boolean create(Dogam domain);

    boolean readExists(MemberId memberId, WasteId wasteId);
}
