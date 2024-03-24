package blisgo.infrastructure.internal.persistence.dictionary.mapper;

import blisgo.domain.dictionary.Waste;
import blisgo.infrastructure.internal.persistence.base.PersistenceMapper;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaWaste;
import blisgo.infrastructure.internal.ui.response.WasteDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WasteMapper implements PersistenceMapper<Waste, JpaWaste, WasteDTO> {
    private final ModelMapper mapper;

    @Override
    public JpaWaste toEntity(Waste domain) {
        return mapper.map(domain, JpaWaste.class);
    }

    @Override
    public Waste toDomain(JpaWaste entity) {
        return mapper.map(entity, Waste.class);
    }

    @Override
    public WasteDTO toDTO(Waste domain) {
        return mapper.map(domain, WasteDTO.class);
    }
}