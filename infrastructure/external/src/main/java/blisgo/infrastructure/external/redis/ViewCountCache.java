package blisgo.infrastructure.external.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Description("게시물 조회수 캐시")
public class ViewCountCache {
    private final RedisTemplate<String, Object> redisTemplate;

    public void increaseViewCount(Long postId) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.increment(getKey(postId), 1);
    }

    public long getViewCount(Long postId) {
        var value = redisTemplate.opsForValue().get(getKey(postId));
        return value == null ? 0 : Long.parseLong(value.toString());
    }

    private String getKey(Long postId) {
        return "post:" + postId + ":views";
    }

    public void remove(Long postId) {
        redisTemplate.delete(getKey(postId));
    }
}