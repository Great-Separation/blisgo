package blisgo.infrastructure.internal.persistence.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "replica" : "source";

        log.debug("Current DataSource is {}", dataSourceName);

        return dataSourceName;
    }
}
