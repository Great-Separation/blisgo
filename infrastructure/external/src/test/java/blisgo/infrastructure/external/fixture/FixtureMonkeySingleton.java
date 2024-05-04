package blisgo.infrastructure.external.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jackson.plugin.JacksonPlugin;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FixtureMonkeySingleton {

    private static FixtureMonkey instance;

    public static synchronized FixtureMonkey getInstance() {
        if (instance == null) {
            instance = FixtureMonkey.builder()
                    .plugin(new JakartaValidationPlugin())
                    .plugin(new JacksonPlugin())
                    .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                    .build();
        }
        return instance;
    }
}
