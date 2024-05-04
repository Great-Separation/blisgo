package blisgo.usecase.request.post;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record GetPost(@Positive Long postId, Pageable pageable) {}
