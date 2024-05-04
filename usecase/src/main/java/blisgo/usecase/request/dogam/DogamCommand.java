package blisgo.usecase.request.dogam;

import org.jmolecules.architecture.hexagonal.PrimaryPort;

@PrimaryPort
public interface DogamCommand {

    boolean addDogam(AddDogam command);

    boolean removeDogam(RemoveDogam command);
}
