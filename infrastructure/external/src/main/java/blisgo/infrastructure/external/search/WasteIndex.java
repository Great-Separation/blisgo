package blisgo.infrastructure.external.search;

import lombok.Builder;

@Builder
public record WasteIndex(
        Long objectID,
        Long wasteId,
        String name,
        String picture
) {
}