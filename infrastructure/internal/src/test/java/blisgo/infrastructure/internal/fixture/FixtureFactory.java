package blisgo.infrastructure.internal.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jackson.plugin.JacksonPlugin;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FixtureFactory {

    public static FixtureMonkey create() {
        return FixtureMonkey.builder()
                .plugin(new JakartaValidationPlugin())
                .plugin(new JacksonPlugin())
                .objectIntrospector(new FailoverIntrospector(List.of(
                        FieldReflectionArbitraryIntrospector.INSTANCE,
                        ConstructorPropertiesArbitraryIntrospector.INSTANCE,
                        BuilderArbitraryIntrospector.INSTANCE)))
                .build();
    }
}
