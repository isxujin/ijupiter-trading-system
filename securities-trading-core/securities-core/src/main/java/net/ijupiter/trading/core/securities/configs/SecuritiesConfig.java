package net.ijupiter.trading.core.securities.configs;

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

import net.ijupiter.trading.core.securities.aggregates.SecuritiesAccount;

/**
 * 证券模块配置类
 */
@Configuration
public class SecuritiesConfig {
    
    /**
     * 证券账户聚合仓储
     */
    @Bean
    public Repository<SecuritiesAccount> securitiesAccountRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(SecuritiesAccount.class)
                .eventStore(eventStore)
                .build();
    }

    /**
     * 证券事件存储
     */
    @Bean
    public EventStore securitiesEventStore(EventStorageEngine eventStorageEngine) {
        return org.axonframework.eventsourcing.eventstore.EmbeddedEventStore.builder()
                .storageEngine(eventStorageEngine)
                .build();
    }
    
    /**
     * 证券命令总线
     */
    @Bean
    public CommandBus securitiesCommandBus() {
        return SimpleCommandBus.builder().build();
    }
    
    /**
     * 证券命令网关
     */
    @Bean
    public CommandGateway securitiesCommandGateway(CommandBus commandBus) {
        return org.axonframework.commandhandling.gateway.DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .build();
    }
}