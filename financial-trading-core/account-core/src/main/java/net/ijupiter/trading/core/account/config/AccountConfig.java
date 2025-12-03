package net.ijupiter.trading.core.account.config;

import net.ijupiter.trading.core.account.aggregates.AccountAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 账户配置
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Configuration
public class AccountConfig {

    /**
     * 账户聚合存储库
     * 
     * @param eventStore 事件存储
     * @return 账户聚合存储库
     */
    @Bean("accountAggregateRepository")
    public Repository<AccountAggregate> accountAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(AccountAggregate.class)
                .eventStore(eventStore)
                .build();
    }
}