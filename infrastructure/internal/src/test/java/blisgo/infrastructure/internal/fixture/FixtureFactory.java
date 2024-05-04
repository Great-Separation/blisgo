package blisgo.infrastructure.internal.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ArbitraryIntrospector;
import com.navercorp.fixturemonkey.jackson.plugin.JacksonPlugin;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FixtureFactory {

    public static FixtureMonkey create(ArbitraryIntrospector introspector) {
        return FixtureMonkey.builder()
                .plugin(new JakartaValidationPlugin())
                .plugin(new JacksonPlugin())
                .objectIntrospector(introspector)
                .build();
    }
}
