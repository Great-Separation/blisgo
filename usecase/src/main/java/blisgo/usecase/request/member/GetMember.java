package blisgo.usecase.request.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder(toBuilder = true)
public record GetMember(@NotEmpty @Email String email) {}
