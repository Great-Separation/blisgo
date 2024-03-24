package blisgo.usecase.port;

import blisgo.domain.dictionary.Dogam;
import blisgo.domain.dictionary.vo.DogamId;
import blisgo.domain.dictionary.vo.WasteId;
import blisgo.domain.member.vo.MemberId;
import blisgo.usecase.request.dogam.AddDogam;
import blisgo.usecase.request.dogam.DogamCommand;
import blisgo.usecase.request.dogam.DogamQuery;
import blisgo.usecase.request.dogam.RemoveDogam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DogamInputPort implements DogamCommand, DogamQuery {
    private final DogamOutputPort port;

    @Override
    public boolean addDogam(AddDogam command) {
        MemberId memberId = MemberId.of(command.memberId());
        WasteId wasteId = WasteId.of(command.wasteId());

        Dogam dogam = Dogam.create(memberId, wasteId);

        return port.create(dogam);
    }

    @Override
    public boolean removeDogam(RemoveDogam command) {
        MemberId memberId = MemberId.of(command.memberId());
        WasteId wasteId = WasteId.of(command.wasteId());

        DogamId dogamId = DogamId.builder()
                .memberId(memberId)
                .wasteId(wasteId)
                .build();

        return port.delete(dogamId);
    }
}
