package blisgo.usecase.request.waste;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record GetWaste(
        Pageable pageable,
        Long wasteId,
        Long lastWasteId
) {
}
