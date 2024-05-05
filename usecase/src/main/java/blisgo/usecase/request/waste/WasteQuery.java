package blisgo.usecase.request.waste;

import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.validation.GetWasteValid;
import blisgo.domain.dictionary.vo.Category;
import blisgo.domain.dictionary.vo.Guide;
import blisgo.usecase.request.dogam.GetDogams;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;

@PrimaryPort
@Validated
public interface WasteQuery {

    Slice<@Valid Waste> getWastes(@Valid GetWastes query);

    @Valid
    @Validated(GetWasteValid.class)
    Waste getWaste(@Valid GetWaste query);

    List<@Valid Guide> getGuides(@NotNull List<Category> categories);

    Slice<@Valid Waste> getWastesFromDogam(@Valid GetDogams query);

    List<@Valid Waste> getWastesRelated(@NotNull List<Category> categories);
}
