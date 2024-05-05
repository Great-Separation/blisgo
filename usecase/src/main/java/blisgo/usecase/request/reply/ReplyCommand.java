package blisgo.usecase.request.reply;

import jakarta.validation.Valid;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.validation.annotation.Validated;

@Validated
@PrimaryPort
public interface ReplyCommand {

    boolean addReply(@Valid AddReply command);

    boolean removeReply(@Valid RemoveReply command);
}
