package blisgo.infrastructure.external.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
@Description("게시물 조회수 캐시")
public class ViewCountCache {
    private final RedisTemplate<String, Object> redisTemplate;

    public long increaseViewCount(Long id, String domain) {
        return Optional.ofNullable(generateKey(id, domain))
                .filter(StringUtils::hasText)
                .map(key -> redisTemplate.opsForValue().increment(key, 1))
                .orElse(0L);
    }

    public long getViewCount(Long id, String domain) {
        return Optional.ofNullable(generateKey(id, domain))
                .filter(StringUtils::hasText)
                .map(key -> redisTemplate.opsForValue().get(key))
                .map(Object::toString)
                .map(Long::parseLong)
                .orElse(0L);
    }

    public boolean removeViewCount(Long postId, String domain) {
        return Optional.ofNullable(generateKey(postId, domain))
                .filter(StringUtils::hasText)
                .map(redisTemplate::delete)
                .orElse(false);
    }

    private String generateKey(Long id, String domain) {
        return Optional.ofNullable(domain)
                .filter(StringUtils::hasText)
                .flatMap(d -> Optional.ofNullable(id)
                        .map(i -> new StringJoiner(":")
                                .add(d)
                                .add(i.toString())
                                .add("views")
                                .toString()))
                .orElse(null);
    }
}