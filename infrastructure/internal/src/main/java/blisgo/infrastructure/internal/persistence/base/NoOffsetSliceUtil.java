package blisgo.infrastructure.internal.persistence.base;

import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class NoOffsetSliceUtil {

    public static boolean checkLastPage(List<?> results, Pageable pageable) {
        if (results.size() > pageable.getPageSize()) {
            results.remove(pageable.getPageSize());
            return true;
        }
        return false;
    }
}
