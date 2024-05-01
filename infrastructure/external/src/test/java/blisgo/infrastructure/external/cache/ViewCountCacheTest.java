package blisgo.infrastructure.external.cache;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(RedisConfigDev.class)
@SpringBootTest(classes = ViewCountCache.class, args = "--spring.profiles.active=dev")
@Testcontainers
class ViewCountCacheTest {

    @Container
    private static final GenericContainer<?> redis = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379);


    @DynamicPropertySource
    public static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", redis::getFirstMappedPort);
    }

    @Autowired
    private ViewCountCache viewCountCache;

    private final String domain = "domain";
    private final Long id = 1L;

    @Nested
    @DisplayName("increaseViewCount 메서드는")
    class increaseViewCount {
        @Test
        @DisplayName("주어진 id와 도메인에 대한 조회수를 증가시킨다")
        void givenIdAndDomain_returnIncreasedViewCount() {
            long viewCount = viewCountCache.increaseViewCount(id, domain);

            assertTrue(viewCount > 0);
        }

        @Test
        @DisplayName("id가 null 경우 0을 반환한다")
        void null_returnZero() {
            long viewCount = viewCountCache.increaseViewCount(null, null);
            assertEquals(0, viewCount);
        }
    }

    @Nested
    @DisplayName("getViewCount 메서드는")
    class GetViewCount {
        @Test
        @DisplayName("주어진 id와 도메인에 대한 조회수를 반환한다")
        void givenIdAndDomain_returnViewCount() {
            long viewCount = viewCountCache.getViewCount(id, domain);
            assertTrue(viewCount > 0);
        }

        @Test
        @DisplayName("id가 null 경우 0을 반환한다")
        void null_returnZero() {
            long viewCount = viewCountCache.getViewCount(null, null);
            assertEquals(0, viewCount);
        }
    }

    @Nested
    @DisplayName("removeViewCount 메서드는")
    class RemoveViewCount {
        @BeforeEach
        void createViewCountCacheFirst() {
            viewCountCache.increaseViewCount(id, domain);
        }

        @Test
        @DisplayName("주어진 id와 도메인에 대한 조회수를 삭제한다")
        void givenIdAndDomain_returnTrue() {
            boolean expected = true;
            boolean actual = viewCountCache.removeViewCount(id, domain);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("id가 null 경우 false 반환한다")
        void null_returnFalse() {
            boolean expected = false;
            boolean actual = viewCountCache.removeViewCount(null, null);

            assertEquals(expected, actual);
        }
    }
}