package blisgo.infrastructure.internal.persistence.community;

import blisgo.domain.community.Reply;
import blisgo.infrastructure.internal.persistence.community.mapper.ReplyMapper;
import blisgo.infrastructure.internal.persistence.community.repository.ReplyCustomRepository;
import blisgo.infrastructure.internal.persistence.community.repository.ReplyJpaRepository;
import blisgo.usecase.port.domain.ReplyOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class ReplyMySQLAdapter implements ReplyOutputPort {
    private final ReplyJpaRepository jpaRepository;
    private final ReplyCustomRepository customRepository;
    private final ReplyMapper mapper;

    @Override
    public boolean delete(Long identifier) {
        return jpaRepository.deleteByReplyId(identifier) > 0;
    }

    @Override
    public boolean create(Reply domain) {
        jpaRepository.save(mapper.toEntity(domain));
        return true;
    }

    @Override
    public Slice<Reply> read(Long postId, Pageable pageable, Long lastReplyId) {
        return customRepository.find(pageable, postId, lastReplyId)
                .map(mapper::toDomain);
    }
}
