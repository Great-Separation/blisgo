package blisgo.usecase.request.waste;

import blisgo.domain.dictionary.vo.Category;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Builder
public record GetWaste(
        Pageable pageable,
        Long wasteId,
        Long lastWasteId,
        List<Category> categories
) {
}
