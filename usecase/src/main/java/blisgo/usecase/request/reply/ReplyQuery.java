package blisgo.usecase.request.reply;

import blisgo.domain.community.Reply;
import blisgo.domain.community.validation.GetRepliesValid;
import jakarta.validation.Valid;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;

@Validated
@PrimaryPort
public interface ReplyQuery {

    @Validated(GetRepliesValid.class)
    Slice<@Valid Reply> getReplies(@Valid GetReplies query);
}
