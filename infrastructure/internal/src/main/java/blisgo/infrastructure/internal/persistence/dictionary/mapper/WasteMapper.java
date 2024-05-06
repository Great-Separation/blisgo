package blisgo.infrastructure.internal.persistence.dictionary.mapper;

import blisgo.domain.dictionary.Waste;
import blisgo.infrastructure.internal.persistence.base.PersistenceMapper;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaWaste;
import blisgo.infrastructure.internal.ui.response.WasteDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        List<String> hashtags = new ArrayList<>();

        var result = mapper.map(entity, Waste.class);

        if (entity.hashtags() != null) {
            String hashtagPersistBag = entity.hashtags().toString();
            if (hashtagPersistBag.startsWith("[") && hashtagPersistBag.endsWith("]")) {
                var strings = hashtagPersistBag.replaceAll("[\\[\\]]", "").split(",");

                Arrays.stream(strings).filter(s -> !s.isBlank()).forEach(s -> hashtags.add(s.trim()));
            }

            Collections.sort(hashtags);

            result = result.toBuilder().hashtags(hashtags).build();
        }

        return result;
    }

    @Override
    public WasteDTO toDTO(Waste domain) {
        return mapper.map(domain, WasteDTO.class);
    }
}
