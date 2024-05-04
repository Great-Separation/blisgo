package blisgo.infrastructure.external.search;

import blisgo.domain.dictionary.Waste;
import org.springframework.stereotype.Component;

@Component
public class WasteIndexMapper implements SearchMapper<Waste, WasteIndex> {

    @Override
    public WasteIndex toIndex(Waste domain) {
        return WasteIndex.builder()
                .objectID(domain.wasteId().id())
                .wasteId(domain.wasteId().id())
                .name(domain.name())
                .picture(domain.picture().url())
                .build();
    }
}
