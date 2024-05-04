package blisgo.infrastructure.internal.ui.response;

import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DogamDTO {

    private String memberId;

    private Long wasteId;

    private OffsetDateTime createdDate;

    private OffsetDateTime modifiedDate;
}
