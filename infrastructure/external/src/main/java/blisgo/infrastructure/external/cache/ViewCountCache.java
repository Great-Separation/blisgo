package blisgo.infrastructure.external.cache;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Description("게시물 조회수 캐시")
public class ViewCountCache {

    private final StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> syncCommands;

    @PostConstruct
    private void init() {
        syncCommands = connection.sync();
    }

    public long increaseViewCount(Long id, String domain) {
        return Optional.ofNullable(generateKey(id, domain))
                .filter(StringUtils::hasText)
                .map(syncCommands::incr)
                .orElse(0L);
    }

    public long getViewCount(Long id, String domain) {
        return Optional.ofNullable(generateKey(id, domain))
                .filter(StringUtils::hasText)
                .map(syncCommands::get)
                .map(Long::parseLong)
                .orElse(0L);
    }

    public boolean removeViewCount(Long postId, String domain) {
        return Optional.ofNullable(generateKey(postId, domain))
                .filter(StringUtils::hasText)
                .map(key -> syncCommands.del(key) > 0)
                .orElse(false);
    }

    private String generateKey(Long id, String domain) {
        return Optional.ofNullable(domain)
                .filter(StringUtils::hasText)
                .flatMap(d -> Optional.ofNullable(id).map(i -> new StringJoiner(":")
                        .add(d)
                        .add(i.toString())
                        .add("views")
                        .toString()))
                .orElse(null);
    }
}
