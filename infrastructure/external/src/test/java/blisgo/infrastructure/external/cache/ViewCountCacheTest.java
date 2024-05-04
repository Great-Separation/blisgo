package blisgo.infrastructure.external.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Import(RedisConfigDev.class)
@SpringBootTest(classes = ViewCountCache.class, args = "--spring.profiles.active=dev")
@Testcontainers
class ViewCountCacheTest {

    @Container
    private static final GenericContainer<?> redis = new GenericContainer<>("redis:latest").withExposedPorts(6379);

    final String domain = "domain";
    final Long id = 1L;

    @Autowired
    private ViewCountCache viewCountCache;

    @DynamicPropertySource
    public static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", redis::getFirstMappedPort);
    }

    @Nested
    @DisplayName("increaseViewCount 메서드는")
    class increaseViewCount {

        @Test
        @DisplayName("주어진 id와 도메인에 대한 조회수를 증가시킨다")
        void idAndDomain_returnIncreasedViewCount() {
            var actual = viewCountCache.increaseViewCount(id, domain);

            assertTrue(actual > 0);
        }

        @Test
        @DisplayName("id가 null 경우 0을 반환한다")
        void null_returnZero() {
            var actual = viewCountCache.increaseViewCount(null, null);

            assertEquals(0, actual);
        }
    }

    @Nested
    @DisplayName("getViewCount 메서드는")
    class getViewCount {

        @Test
        @DisplayName("주어진 id와 도메인에 대한 조회수를 반환한다")
        void idAndDomain_returnViewCount() {
            var actual = viewCountCache.getViewCount(id, domain);

            assertTrue(actual > 0);
        }

        @Test
        @DisplayName("id가 null 경우 0을 반환한다")
        void null_returnZero() {
            var actual = viewCountCache.getViewCount(null, null);

            assertEquals(0, actual);
        }
    }

    @Nested
    @DisplayName("removeViewCount 메서드는")
    class removeViewCount {

        @BeforeEach
        void createViewCountCache() {
            viewCountCache.increaseViewCount(id, domain);
        }

        @Test
        @DisplayName("주어진 id와 도메인에 대한 조회수를 삭제한다")
        void idAndDomain_returnTrue() {
            var expected = true;
            var actual = viewCountCache.removeViewCount(id, domain);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("id가 null 경우 false 반환한다")
        void null_returnFalse() {
            var expected = false;
            var actual = viewCountCache.removeViewCount(null, null);

            assertEquals(expected, actual);
        }
    }
}
