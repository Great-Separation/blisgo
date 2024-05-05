package blisgo.infrastructure.internal.persistence.dictionary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import blisgo.domain.dictionary.Waste;
import blisgo.domain.dictionary.vo.Category;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.JpaConfig;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.GuideMapper;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.WasteMapper;
import blisgo.infrastructure.internal.persistence.dictionary.repository.GuideJpaRepository;
import blisgo.infrastructure.internal.persistence.dictionary.repository.WasteCustomRepository;
import blisgo.infrastructure.internal.persistence.dictionary.repository.WasteJpaRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@ContextConfiguration(
        classes = {
            JacksonConfig.class,
            JsonParser.class,
            Jackson2ObjectMapperBuilder.class,
            WasteCustomRepository.class,
            WasteJpaRepository.class,
            WastePersistenceAdapter.class,
            WasteMapper.class,
            GuideMapper.class,
            JpaConfig.class,
            MapperConfig.class
        })
@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:h2-waste-persistence-adapter.sql"})
class WastePersistenceAdapterTest {

    final RandomGenerator randomGenerator = RandomGenerator.getDefault();
    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final int REPEAT = 10;

    @Autowired
    WasteJpaRepository wasteJpaRepository;

    @Autowired
    WasteCustomRepository wasteCustomRepository;

    @Autowired
    GuideJpaRepository guideJpaRepository;

    @Autowired
    WasteMapper wasteMapper;

    @Autowired
    GuideMapper guideMapper;

    WastePersistenceAdapter wastePersistenceAdapter;

    @BeforeEach
    public void setup() {
        wastePersistenceAdapter = new WastePersistenceAdapter(
                wasteJpaRepository, wasteCustomRepository, guideJpaRepository, wasteMapper, guideMapper);
    }

    @Nested
    class R {

        @Test
        @DisplayName("폐기물 조회(단건)")
        void read() {
            for (int i = 0; i < REPEAT; i++) {
                var actual = wastePersistenceAdapter.read(randomGenerator.nextLong(1001, 1010));
                assertNotNull(actual);
            }
        }

        @Test
        @DisplayName("폐기물 조회(다건)")
        void readMultiple() {
            for (int i = 0; i < REPEAT; i++) {
                var lastWasteId = randomGenerator.nextLong(1001, 1010);
                Supplier<Slice<Waste>> callReadMultiple =
                        () -> wastePersistenceAdapter.read(Pageable.ofSize(10), lastWasteId);

                assertTrue(callReadMultiple.get().hasContent());
                assertDoesNotThrow(callReadMultiple::get);
            }
        }

        @Test
        @DisplayName("폐기물 내 가이드 조회")
        void readGuides() {
            for (int i = 0; i < REPEAT; i++) {
                var categories = fixtureMonkey
                        .giveMeBuilder(Category.class)
                        .sampleList(randomGenerator.nextInt(0, Category.values().length));

                var guides = wastePersistenceAdapter.readGuides(categories);

                for (var guide : guides) {
                    assertDoesNotThrow(() -> Category.valueOf(guide.category().name()));
                }
            }
        }

        @Test
        @DisplayName("도감(사용자가 북마크한 폐기물) 조회")
        void readWastesFromDogam() {
            for (int i = 0; i < REPEAT; i++) {
                var memberId = UUID.nameUUIDFromBytes("okjaeook98@gmail.com".getBytes());
                var pageable = Pageable.ofSize(10);
                var lastDogamCreatedDate = OffsetDateTime.now();

                var actual = wastePersistenceAdapter.readWastesFromDogam(memberId, pageable, lastDogamCreatedDate);
                assertNotNull(actual);
            }
        }

        @Test
        @DisplayName("관련 폐기물 조회")
        void readWastesRelated() {
            var categories = fixtureMonkey
                    .giveMeBuilder(Category.class)
                    .sampleList(randomGenerator.nextInt(0, Category.values().length));

            Supplier<List<Waste>> readWastesRelated = () -> wastePersistenceAdapter.readWastesRelated(categories);

            assertNotNull(readWastesRelated.get());
            assertDoesNotThrow(readWastesRelated::get);
        }
    }
}
