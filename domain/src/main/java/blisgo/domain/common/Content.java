package blisgo.domain.common;

import blisgo.domain.community.validation.GetPostValid;
import blisgo.domain.community.validation.GetPostsValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    @NotEmpty(groups = {GetPostValid.class})
    private String text;

    @NotNull(groups = {GetPostsValid.class})
    private Picture thumbnail;

    @NotEmpty(groups = {GetPostsValid.class})
    private String preview;
}
