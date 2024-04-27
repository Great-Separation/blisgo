package blisgo.infrastructure.external.database;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class WasteDirectDBAdapter implements ViewCountable {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Long> findIds() {
        var sql = "SELECT waste_id FROM waste";
        return jdbcTemplate.queryForList(sql, Long.class);
    }

    @Override
    public boolean updateViewCount(Long wasteId, Long increment) {
        var sql = "UPDATE waste SET views = views + ? WHERE waste_id = ?";
        return jdbcTemplate.update(sql, increment, wasteId) > 0;
    }

    public boolean updatePopularity() {
        var sql = "UPDATE waste "
                + "JOIN (SELECT waste_id, NTILE(10) OVER (ORDER BY views) AS star FROM waste) AS w2 "
                + "SET popularity = w2.star "
                + "WHERE waste.waste_id = w2.waste_id";
        jdbcTemplate.update(sql);
        return true;
    }

    public List<Map<String, String>> findAll() {
        var sql = "SELECT waste.waste_id, name, picture, wh.hashtags " +
                "FROM waste " +
                "JOIN waste_hashtags AS wh " +
                "ON waste.waste_id = wh.waste_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> Map.of(
                "waste_id", rs.getString("waste_id"),
                "name", rs.getString("name"),
                "picture", rs.getString("picture"),
                "hashtags", rs.getString("hashtags")
        ));
    }

}
