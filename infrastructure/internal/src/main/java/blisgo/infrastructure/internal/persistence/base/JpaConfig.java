package blisgo.infrastructure.internal.persistence.base;

import blisgo.infrastructure.internal.InternalRoot;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = InternalRoot.class)
@EntityScan(basePackageClasses = InternalRoot.class)
public class JpaConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    @Description("QueryDSL JPA 설정")
    public JPAQueryFactory init() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    @Description("도메인 중 회원 정보 데이터가 필요한 경우 OIDC 에서 받아온 회원 정보를 사용하도록 함")
    public AuditorAware<UUID> auditorProvider() {
        return new OidcAuditorAware();
    }

    @Bean(name = "auditingDateTimeProvider")
    @Description("도메인 중 생성일, 수정일 데이터 시각을 OffsetDateTime 으로 제어")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}
