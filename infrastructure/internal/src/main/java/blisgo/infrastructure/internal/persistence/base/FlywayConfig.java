package blisgo.infrastructure.internal.persistence.base;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'blisgo';";

    @Bean
    public Flyway flyway(@Qualifier("sourceDataSource") DataSource sourceDataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(sourceDataSource)
                .locations("classpath:db/migration") // 마이그레이션 스크립트 위치
                .baselineOnMigrate(true) // 마이그레이션을 시작하기 전에 베이스라인을 설정
                .initSql(sql)
                .load();

        // flyway.migrate();

        return flyway;
    }
}
