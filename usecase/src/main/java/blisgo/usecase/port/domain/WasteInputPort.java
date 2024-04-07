package blisgo.usecase.port.domain;

import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.vo.Category;
import blisgo.domain.dictionary.vo.Guide;
import blisgo.domain.member.vo.MemberId;
import blisgo.usecase.request.dogam.GetDogam;
import blisgo.usecase.request.waste.GetWaste;
import blisgo.usecase.request.waste.WasteQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WasteInputPort implements WasteQuery {
    private final WasteOutputPort port;

    @Override
    public Slice<Waste> getWastes(GetWaste query) {
        return port.read(query.pageable(), query.lastWasteId());
    }

    @Override
    public Waste getWaste(GetWaste query) {
        return port.read(query.wasteId());
    }

    @Override
    public List<Guide> getGuides(List<Category> categories) {
        return port.readGuides(categories);
    }

    @Override
    public Slice<Waste> getWastesFromDogam(GetDogam query) {
        return port.readWastesFromDogam(MemberId.of(query.email()).id(), query.pageable(), query.lastDogamCreatedDate());
    }

    @Override
    public List<Waste> getWastesRelated(List<Category> categories) {
        return port.readWastesRelated(categories);
    }

    @Override
    public List<Waste> getWastesRelated(GetWaste query) {
        return port.readWastesRelated(query.categories());
    }
}
