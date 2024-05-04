package blisgo.infrastructure.external.database;

import java.util.List;

public interface ViewCountable {

    List<Long> findIds();

    boolean updateViewCount(Long id, Long viewCount);
}
