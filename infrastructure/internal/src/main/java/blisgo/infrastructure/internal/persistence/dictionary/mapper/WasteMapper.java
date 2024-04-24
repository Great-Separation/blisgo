package blisgo.infrastructure.internal.persistence.dictionary.mapper;

import blisgo.domain.dictionary.Waste;
import blisgo.infrastructure.internal.persistence.base.PersistenceMapper;
import blisgo.infrastructure.internal.persistence.dictionary.model.JpaWaste;
import blisgo.infrastructure.internal.ui.response.WasteDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                var strs = hashtagPersistBag
                        .replaceAll("[\\[\\]]", "")
                        .split(",");

                for (String s : strs) {
                    if (!s.isBlank()) {
                        hashtags.add(s.trim());
                    }
                }
            }

            Collections.sort(hashtags);

            result = result.toBuilder()
                    .hashtags(hashtags)
                    .build();
        }


        return result;
    }

    @Override
    public WasteDTO toDTO(Waste domain) {
        return mapper.map(domain, WasteDTO.class);
    }

}