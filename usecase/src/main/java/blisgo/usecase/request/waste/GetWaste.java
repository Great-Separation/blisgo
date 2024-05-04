package blisgo.usecase.request.waste;

import blisgo.domain.dictionary.vo.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record GetWaste(
        Pageable pageable, @NotNull @Positive Long wasteId, @Positive Long lastWasteId, List<Category> categories) {}
