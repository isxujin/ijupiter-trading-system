package net.ijupiter.trading.core.fund.config;

import net.ijupiter.trading.core.fund.aggregates.FundAccountAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 资金配置
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Configuration
public class FundConfig {

    /**
     * 资金账户聚合存储库
     * 
     * @param eventStore 事件存储
     * @return 资金账户聚合存储库
     */
    @Bean("fundAccountAggregateRepository")
    public Repository<FundAccountAggregate> fundAccountAggregateRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(FundAccountAggregate.class)
                .eventStore(eventStore)
                .build();
    }
}