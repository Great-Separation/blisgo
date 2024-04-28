package blisgo.usecase.port.domain;

import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.vo.Category;
import blisgo.domain.dictionary.vo.Guide;
import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@SecondaryPort
public interface WasteOutputPort {
    Waste read(Long wasteId);

    Slice<Waste> read(Pageable pageable, Long lastWasteId);

    List<Guide> readGuides(List<Category> categories);

    Slice<Waste> readWastesFromDogam(UUID memberId, Pageable pageable, OffsetDateTime lastDogamCreatedDate);

    List<Waste> readWastesRelated(List<Category> categories);
}
