package blisgo.infrastructure.internal.persistence.dictionary.mapper;

import blisgo.domain.dictionary.Dogam;
import blisgo.infrastructure.internal.persistence.base.PersistenceMapper;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaDogam;
import blisgo.infrastructure.internal.ui.response.DogamDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DogamMapper implements PersistenceMapper<Dogam, JpaDogam, DogamDTO> {
    private final ModelMapper mapper;

    @Override
    public JpaDogam toEntity(Dogam domain) {
        return mapper.map(domain, JpaDogam.class);
    }

    @Override
    public Dogam toDomain(JpaDogam entity) {
        return mapper.map(entity, Dogam.class);
    }

    @Override
    public DogamDTO toDTO(Dogam domain) {
        return mapper.map(domain, DogamDTO.class);
    }
}