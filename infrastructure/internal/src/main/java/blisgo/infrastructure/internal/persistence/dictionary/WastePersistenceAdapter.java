package blisgo.infrastructure.internal.persistence.dictionary;

import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.vo.Category;
import blisgo.domain.dictionary.vo.Guide;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.GuideMapper;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.persistence.dictionary.repository.GuideJpaRepository;
import blisgo.infrastructure.internal.persistence.dictionary.repository.WasteCustomRepository;
import blisgo.infrastructure.internal.persistence.dictionary.repository.WasteJpaRepository;
import blisgo.usecase.port.domain.WasteOutputPort;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WastePersistenceAdapter implements WasteOutputPort {

    private final WasteJpaRepository wasteJpaRepository;

    private final WasteCustomRepository wasteCustomRepository;

    private final GuideJpaRepository guideJpaRepository;

    private final WasteMapper wasteMapper;

    private final GuideMapper guideMapper;

    @Override
    public Waste read(Long wasteId) {
        return wasteJpaRepository
                .findFirstByWasteId(wasteId)
                .map(wasteMapper::toDomain)
                .orElse(null);
    }

    @Override
    public Slice<Waste> read(Pageable pageable, Long lastWasteId) {
        return wasteCustomRepository.findPartial(pageable, lastWasteId).map(wasteMapper::toDomain);
    }

    @Override
    public List<Guide> readGuides(List<Category> categories) {
        return guideMapper.toDomains(guideJpaRepository.findAllById(categories));
    }

    @Override
    public Slice<Waste> readWastesFromDogam(UUID memberId, Pageable pageable, OffsetDateTime lastDogamCreatedDate) {
        return wasteCustomRepository
                .findWastesByMemberIdFromDogam(memberId, pageable, lastDogamCreatedDate)
                .map(wasteMapper::toDomain);
    }

    @Override
    public List<Waste> readWastesRelated(List<Category> categories) {
        return wasteMapper.toDomains(wasteCustomRepository.findWastesByCategories(categories));
    }
}
