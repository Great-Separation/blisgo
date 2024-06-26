package blisgo.infrastructure.internal.persistence.dictionary;

import blisgo.domain.dictionary.Dogam;
import blisgo.domain.dictionary.vo.DogamId;
import blisgo.domain.dictionary.vo.WasteId;
import blisgo.domain.member.vo.MemberId;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.DogamMapper;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaDogamId;
import blisgo.infrastructure.internal.persistence.dictionary.repository.DogamJpaRepository;
import blisgo.usecase.port.domain.DogamOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DogamPersistenceAdapter implements DogamOutputPort {

    private final DogamJpaRepository jpaRepository;

    private final DogamMapper mapper;

    @Override
    @Transactional
    public boolean delete(DogamId identifier) {
        JpaDogamId jpaDogamId =
                JpaDogamId.of(identifier.memberId().id(), identifier.wasteId().id());

        return jpaRepository.deleteByDogamId(jpaDogamId) > 0;
    }

    @Override
    @Transactional
    public boolean create(Dogam domain) {
        jpaRepository.save(mapper.toEntity(domain));
        return true;
    }

    @Override
    public boolean readExists(MemberId memberId, WasteId wasteId) {
        JpaDogamId dogamId = JpaDogamId.of(memberId.id(), wasteId.id());
        return jpaRepository.existsByDogamId(dogamId);
    }
}
