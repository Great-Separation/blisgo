package blisgo.domain.dictionary.vo;

import blisgo.domain.common.Picture;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guide {
    private Category category;
    private String content;
    private Picture picture;
}
