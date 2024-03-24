package blisgo.infrastructure.internal.persistence.member.mapper;

import blisgo.domain.member.Member;
import blisgo.infrastructure.internal.persistence.base.PersistenceMapper;
import blisgo.infrastructure.internal.persistence.member.model.JpaMember;
import blisgo.infrastructure.internal.ui.response.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMapper implements PersistenceMapper<Member, JpaMember, MemberDTO> {
    private final ModelMapper mapper;

    @Override
    public JpaMember toEntity(Member domain) {
        return mapper.map(domain, JpaMember.class);
    }

    @Override
    public Member toDomain(JpaMember entity) {
        return mapper.map(entity, Member.class);
    }

    @Override
    public MemberDTO toDTO(Member domain) {
        return mapper.map(domain, MemberDTO.class);
    }
}