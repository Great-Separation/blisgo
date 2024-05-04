package blisgo.usecase.request.member;

import blisgo.domain.base.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UpdateMember(
        @Email String email, @NotEmpty String name, @Pattern(regexp = Constants.HTTP_PROTOCOL) String picture) {}
