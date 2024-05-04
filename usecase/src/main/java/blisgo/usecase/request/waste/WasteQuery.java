package blisgo.usecase.request.waste;

import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.vo.Category;
import blisgo.domain.dictionary.vo.Guide;
import blisgo.usecase.request.dogam.GetDogam;
import java.util.List;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.data.domain.Slice;

@PrimaryPort
public interface WasteQuery {

    Slice<Waste> getWastes(GetWaste query);

    Waste getWaste(GetWaste query);

    List<Guide> getGuides(List<Category> categories);

    Slice<Waste> getWastesFromDogam(GetDogam query);

    List<Waste> getWastesRelated(List<Category> categories);

    List<Waste> getWastesRelated(GetWaste query);
}
