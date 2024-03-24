package blisgo.infrastructure.internal.persistence.community.mapper;

import blisgo.domain.community.Reply;
import blisgo.infrastructure.internal.persistence.base.PersistenceMapper;
import blisgo.infrastructure.internal.persistence.community.model.JpaReply;
import blisgo.infrastructure.internal.ui.response.ReplyDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyMapper implements PersistenceMapper<Reply, JpaReply, ReplyDTO> {
    private final ModelMapper mapper;

    @Override
    public JpaReply toEntity(Reply domain) {
        return mapper.map(domain, JpaReply.class);
    }

    @Override
    public Reply toDomain(JpaReply entity) {
        return mapper.map(entity, Reply.class);
    }

    @Override
    public ReplyDTO toDTO(Reply domain) {
        return mapper.map(domain, ReplyDTO.class);
    }
}