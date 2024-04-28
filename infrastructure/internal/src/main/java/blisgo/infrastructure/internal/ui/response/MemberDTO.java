package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.common.Picture;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDTO {
    private UUID id;
    private String name;
    private String email;
    private Picture picture;
    private OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;
}

