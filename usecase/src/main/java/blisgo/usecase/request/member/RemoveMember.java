package blisgo.usecase.request.member;

import lombok.Builder;

@Builder
public record RemoveMember(
        String email
)  {
}
