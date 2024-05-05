package blisgo.infrastructure.internal.persistence.dictionary;

import static org.junit.jupiter.api.Assertions.assertTrue;

import blisgo.domain.dictionary.Dogam;
import blisgo.domain.dictionary.vo.DogamId;
import blisgo.domain.dictionary.vo.WasteId;
import blisgo.domain.member.vo.MemberId;
import blisgo.infrastructure.external.extract.JacksonConfig;
import blisgo.infrastructure.external.extract.JsonParser;
import blisgo.infrastructure.internal.fixture.FixtureFactory;
import blisgo.infrastructure.internal.persistence.base.JpaConfig;
import blisgo.infrastructure.internal.persistence.base.MapperConfig;
import blisgo.infrastructure.internal.persistence.dictionary.mapper.DogamMapper;
import blisgo.infrastructure.internal.persistence.dictionary.repository.DogamJpaRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
            DogamPersistenceAdapter.class,
            DogamMapper.class,
            JpaConfig.class,
            MapperConfig.class
        })
@ActiveProfiles("test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = "TRUNCATE TABLE dogam RESTART IDENTITY;")
class DogamPersistenceAdapterTest {

    final FixtureMonkey fixtureMonkey = FixtureFactory.create();
    final int REPEAT = 10;

    @Autowired
    DogamJpaRepository dogamJpaRepository;

    @Autowired
    DogamMapper dogamMapper;

    DogamPersistenceAdapter dogamPersistenceAdapter;

    @BeforeEach
    public void setup() {
        dogamPersistenceAdapter = new DogamPersistenceAdapter(dogamJpaRepository, dogamMapper);
    }

    @Nested
    class C {

        @RepeatedTest(REPEAT)
        @DisplayName("도감 생성")
        void create() {
            Dogam dogam = fixtureMonkey.giveMeOne(Dogam.class);

            var actual = dogamPersistenceAdapter.create(dogam);

            assertTrue(actual);
        }
    }

    @Nested
    class RD {

        MemberId memberId = fixtureMonkey.giveMeOne(MemberId.class);

        WasteId wasteId = fixtureMonkey.giveMeOne(WasteId.class);

        DogamId dogamId = fixtureMonkey
                .giveMeBuilder(DogamId.class)
                .set("memberId", memberId)
                .set("wasteId", wasteId)
                .sample();

        @BeforeEach
        void setup() {
            Dogam dogam = fixtureMonkey
                    .giveMeBuilder(Dogam.class)
                    .set("dogamId", dogamId)
                    .sample();

            dogamPersistenceAdapter.create(dogam);
        }

        @Test
        @DisplayName("도감 삭제")
        void delete() {
            var actual = dogamPersistenceAdapter.delete(dogamId);
            assertTrue(actual);
        }

        @Test
        @DisplayName("도감 유무 조회")
        void readExists() {
            var actual = dogamPersistenceAdapter.readExists(memberId, wasteId);
            assertTrue(actual);
        }
    }
}
