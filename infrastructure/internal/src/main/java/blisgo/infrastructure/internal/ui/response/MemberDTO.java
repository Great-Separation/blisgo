package blisgo.infrastructure.internal.ui.response;

import blisgo.domain.common.Picture;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDTO {
    private UUID id;
    private String name;
    private String email;
    private Picture picture;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}

