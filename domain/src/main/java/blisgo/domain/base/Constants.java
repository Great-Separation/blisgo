package blisgo.domain.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String HEX_COLOR_PATTERN = "^#([A-Fa-f0-9]{6})$";
    public static final String HTTP_PROTOCOL = "^(http://|https://).*$";
}
