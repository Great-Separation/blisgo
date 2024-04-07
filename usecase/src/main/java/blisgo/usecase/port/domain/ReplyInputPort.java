package blisgo.usecase.port.domain;

import blisgo.domain.community.Reply;
import blisgo.domain.community.vo.PostId;
import blisgo.usecase.request.reply.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyInputPort implements ReplyCommand, ReplyQuery {
    private final ReplyOutputPort port;

    @Override
    public boolean addReply(AddReply command) {
        var reply = Reply.builder()
                .postId(PostId.of(command.postId()))
                .content(command.content())
                .build();

        return port.create(reply);
    }

    @Override
    public boolean removeReply(RemoveReply command) {
        return port.delete(command.replyId());
    }

    @Override
    public Slice<Reply> getReplies(GetReply query) {
        return port.read(query.postId(), query.pageable(), query.lastReplyId());
    }
}
