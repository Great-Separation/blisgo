package blisgo.app;

import blisgo.domain.DomainRoot;
import blisgo.infrastructure.external.ExternalRoot;
import blisgo.infrastructure.internal.InternalRoot;
import blisgo.usecase.UseCaseRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackageClasses = {
        InternalRoot.class,
        DomainRoot.class,
        ExternalRoot.class,
        UseCaseRoot.class
})
public class BlisgoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlisgoApplication.class, args);
    }
}
