package blisgo.infrastructure.external.database;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDirectDBAdapter implements ViewCountable {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Long> findIds() {
        var sql = "SELECT post_id FROM post";
        return jdbcTemplate.queryForList(sql, Long.class);
    }

    @Override
    public boolean updateViewCount(Long id, Long increment) {
        var sql = "UPDATE post SET views = views + ? WHERE post_id = ?";
        return jdbcTemplate.update(sql, increment, id) > 0;
    }
}
