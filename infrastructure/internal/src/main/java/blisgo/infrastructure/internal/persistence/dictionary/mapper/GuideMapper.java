package blisgo.infrastructure.internal.persistence.dictionary.mapper;

import blisgo.domain.dictionary.vo.Guide;
import blisgo.infrastructure.internal.persistence.base.PersistenceMapper;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaGuide;
import blisgo.infrastructure.internal.ui.response.GuideDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuideMapper implements PersistenceMapper<Guide, JpaGuide, GuideDTO> {
    private final ModelMapper mapper;

    @Override
    public JpaGuide toEntity(Guide domain) {
        return mapper.map(domain, JpaGuide.class);
    }

    @Override
    public Guide toDomain(JpaGuide entity) {
        return mapper.map(entity, Guide.class);
    }

    @Override
    public GuideDTO toDTO(Guide domain) {
        return mapper.map(domain, GuideDTO.class);
    }
}
