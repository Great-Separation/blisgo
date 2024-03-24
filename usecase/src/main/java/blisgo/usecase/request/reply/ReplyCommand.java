package blisgo.usecase.request.reply;

import org.jmolecules.architecture.hexagonal.PrimaryPort;

@PrimaryPort
public interface ReplyCommand {
    boolean addReply(AddReply command);

    boolean removeReply(RemoveReply command);
}
