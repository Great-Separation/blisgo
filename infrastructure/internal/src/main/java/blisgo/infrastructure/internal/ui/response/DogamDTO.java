package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.dictionary.Waste;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DogamDTO {
    private Long memberId;
    private Long wasteId;
    private Waste waste;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
