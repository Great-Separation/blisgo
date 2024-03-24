package blisgo.infrastructure.internal.persistence.base;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;

import java.util.List;

@UtilityClass
public class NoOffsetSliceHelper {
    public static boolean checkLastPage(List<?> results, Pageable pageable) {
        if (results.size() > pageable.getPageSize()) {
            results.remove(pageable.getPageSize());
            return true;
        }
        return false;
    }
}
