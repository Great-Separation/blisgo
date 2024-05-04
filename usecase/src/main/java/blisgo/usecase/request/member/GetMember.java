package blisgo.usecase.request.member;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record GetMember(@Email String email) {}
