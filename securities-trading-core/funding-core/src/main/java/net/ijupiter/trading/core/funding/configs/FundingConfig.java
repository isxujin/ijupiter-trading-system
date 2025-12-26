package net.ijupiter.trading.core.funding.configs;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.modelling.command.Repository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import net.ijupiter.trading.core.funding.aggregates.FundingAccount;

/**
 * 资金模块配置类
 */
@Configuration
public class FundingConfig {
    
    /**
     * 资金账户聚合仓储
     */
    @Bean
    public Repository<FundingAccount> fundingAccountRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(FundingAccount.class)
                .eventStore(eventStore)
                .build();
    }

    /**
     * 资金事件存储
     */
    @Bean
    public EventStore fundingEventStore(EventStorageEngine eventStorageEngine) {
        return org.axonframework.eventsourcing.eventstore.EmbeddedEventStore.builder()
                .storageEngine(eventStorageEngine)
                .build();
    }
    
    /**
     * 资金命令总线
     */
    @Bean
    public CommandBus fundingCommandBus() {
        return SimpleCommandBus.builder().build();
    }
    
    /**
     * 资金命令网关
     */
    @Bean
    public CommandGateway fundingCommandGateway(CommandBus commandBus) {
        return org.axonframework.commandhandling.gateway.DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .build();
    }
}