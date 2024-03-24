package blisgo.infrastructure.internal.persistence.dictionary;

import blisgo.domain.dictionary.Dogam;
import blisgo.domain.dictionary.vo.DogamId;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.DogamMapper;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaDogamId;
import blisgo.infrastructure.internal.persistence.dictionary.repository.DogamCustomRepository;
import blisgo.infrastructure.internal.persistence.dictionary.repository.DogamJpaRepository;
import blisgo.usecase.port.DogamOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DogamMySQLAdapter implements DogamOutputPort {
    private final DogamJpaRepository jpaRepository;
    private final DogamCustomRepository customRepository;
    private final DogamMapper mapper;
    private final WasteMapper wasteMapper;

    @Override
    @Transactional
    public boolean delete(DogamId identifier) {
        JpaDogamId jpaDogamId = JpaDogamId.of(
                identifier.memberId().id(),
                identifier.wasteId().id()
        );

        return jpaRepository.deleteByDogamId(jpaDogamId);
    }

    @Override
    @Transactional
    public boolean create(Dogam domain) {
        jpaRepository.save(mapper.toEntity(domain));
        return true;
    }
}
