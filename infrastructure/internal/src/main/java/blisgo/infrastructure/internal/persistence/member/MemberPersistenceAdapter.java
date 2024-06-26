package blisgo.infrastructure.internal.persistence.member;

import blisgo.domain.member.Member;
import blisgo.infrastructure.internal.persistence.member.mapper.MemberMapper;
import blisgo.infrastructure.internal.persistence.member.repository.MemberJpaRepository;
import blisgo.usecase.port.domain.MemberOutputPort;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberOutputPort {

    private final MemberJpaRepository jpaRepository;

    private final MemberMapper mapper;

    @Override
    @Transactional
    public boolean update(Member domain) {
        var jpaMember = mapper.toEntity(domain);

        jpaRepository
                .findFirstByEmail(jpaMember.email())
                .ifPresentOrElse(
                        existingMember -> existingMember.updateInfo(jpaMember), () -> jpaRepository.save(jpaMember));

        return true;
    }

    @Override
    @Transactional
    public boolean delete(String email) {
        return jpaRepository.deleteByEmail(email) > 0;
    }

    @Override
    @Transactional
    public boolean create(Member domain) {
        jpaRepository.save(mapper.toEntity(domain));
        return true;
    }

    @Override
    public Member read(Map<String, ?> columns) {
        String email = String.valueOf(columns.get("email"));

        return jpaRepository.findFirstByEmail(email).map(mapper::toDomain).orElse(null);
    }
}
