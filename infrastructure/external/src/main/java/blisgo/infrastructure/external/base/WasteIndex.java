package blisgo.infrastructure.external.base;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record WasteIndex(
        Long objectID,
        Long wasteId,
        String name,
        String type,
        String picture,
        Long views,
        List<String> categories,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {
}