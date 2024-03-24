package blisgo.usecase.request.member;

import lombok.Builder;

@Builder
public record UpdateMember(
        String email,
        String name,
        String picture
)  {
}
