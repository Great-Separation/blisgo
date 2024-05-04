package blisgo.usecase.request.member;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record RemoveMember(@Email String email) {}
