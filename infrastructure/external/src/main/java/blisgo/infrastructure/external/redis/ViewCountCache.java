package blisgo.infrastructure.external.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
@Description("게시물 조회수 캐시")
public class ViewCountCache {
    private final RedisTemplate<String, Object> redisTemplate;

    public void increaseViewCount(Long id, String domain) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.increment(getKey(id, domain), 1);
    }

    public long getViewCount(Long id, String domain) {
        var value = redisTemplate.opsForValue().get(getKey(id, domain));
        return value == null ? 0 : Long.parseLong(value.toString());
    }

    private String getKey(Long id, String domain) {
        return new StringJoiner(":")
                .add(domain)
                .add(id.toString())
                .add("views")
                .toString();
    }

    public void remove(Long postId, String domain) {
        redisTemplate.delete(getKey(postId, domain));
    }
}