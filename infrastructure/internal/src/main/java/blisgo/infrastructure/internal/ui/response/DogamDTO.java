package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.dictionary.Waste;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DogamDTO {
    private Long memberId;
    private Long wasteId;
    private Waste waste;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
