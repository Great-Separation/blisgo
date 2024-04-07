package blisgo.usecase.port.domain;

import blisgo.domain.community.Reply;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@SecondaryPort
public interface ReplyOutputPort {
    boolean delete(Long identifier);

    boolean create(Reply domain);

    Slice<Reply> read(Long postId, Pageable pageable, Long lastReplyId);
}
