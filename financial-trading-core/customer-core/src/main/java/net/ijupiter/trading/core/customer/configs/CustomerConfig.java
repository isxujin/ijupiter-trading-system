package net.ijupiter.trading.core.customer.configs;

import net.ijupiter.trading.core.customer.aggregates.CustomerAggregate;
import net.ijupiter.trading.core.customer.aggregates.TradingAccountAggregate;
import net.ijupiter.trading.core.customer.aggregates.FundAccountAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 客户模块配置
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Configuration
public class CustomerConfig {

    /**
     * 客户聚合事件存储仓库
     * 
     * @param eventStore 事件存储
     * @return 客户聚合事件存储仓库
     */
    @Bean
    public EventSourcingRepository<CustomerAggregate> customerAggregateEventSourcingRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(CustomerAggregate.class)
                .eventStore(eventStore)
                .build();
    }

    /**
     * 交易账户聚合事件存储仓库
     * 
     * @param eventStore 事件存储
     * @return 交易账户聚合事件存储仓库
     */
    @Bean
    public EventSourcingRepository<TradingAccountAggregate> tradingAccountAggregateEventSourcingRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(TradingAccountAggregate.class)
                .eventStore(eventStore)
                .build();
    }

    /**
     * 资金账户聚合事件存储仓库
     * 
     * @param eventStore 事件存储
     * @return 资金账户聚合事件存储仓库
     */
    @Bean
    public EventSourcingRepository<FundAccountAggregate> fundAccountAggregateEventSourcingRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(FundAccountAggregate.class)
                .eventStore(eventStore)
                .build();
    }
}