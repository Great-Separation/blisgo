package blisgo.usecase.port;

import blisgo.domain.dictionary.Dogam;
import blisgo.domain.dictionary.vo.DogamId;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

@SecondaryPort
public interface DogamOutputPort {
    boolean delete(DogamId identifier);

    boolean create(Dogam domain);
}
