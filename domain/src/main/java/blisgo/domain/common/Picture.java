package blisgo.domain.common;

import blisgo.domain.base.Constants;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picture {

    @Pattern(regexp = Constants.HTTP_PROTOCOL)
    private String url;
}
