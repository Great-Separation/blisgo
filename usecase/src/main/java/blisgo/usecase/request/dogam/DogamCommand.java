package blisgo.usecase.request.dogam;

import jakarta.validation.Valid;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.validation.annotation.Validated;

@Validated
@PrimaryPort
public interface DogamCommand {

    boolean addDogam(@Valid AddDogam command);

    boolean removeDogam(@Valid RemoveDogam command);
}
