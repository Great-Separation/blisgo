package blisgo.usecase.request.reply;

import blisgo.domain.community.Reply;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.data.domain.Slice;

@PrimaryPort
public interface ReplyQuery {

    Slice<Reply> getReplies(GetReply query);
}
