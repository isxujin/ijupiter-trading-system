package net.ijupiter.trading.core.settlement.configs;

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

import net.ijupiter.trading.core.settlement.aggregates.Settlement;

/**
 * 清算模块配置类
 */
@Configuration
public class SettlementConfig {
    
    /**
     * 清算聚合仓储
     */
    @Bean
    public Repository<Settlement> settlementRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(Settlement.class)
                .eventStore(eventStore)
                .build();
    }
    
    /**
     * 事件存储
     */
    @Bean
    public EventStore settlementEventStore(EventStorageEngine settlementEventStorageEngine) {
        return org.axonframework.eventsourcing.eventstore.EmbeddedEventStore.builder()
                .storageEngine(settlementEventStorageEngine)
                .build();
    }
    
    /**
     * 命令总线
     */
    @Bean
    public CommandBus settlementCommandBus() {
        return SimpleCommandBus.builder().build();
    }
    
    /**
     * 命令网关
     */
    @Bean
    public CommandGateway settlementCommandGateway(CommandBus settlementCommandBus) {
        return org.axonframework.commandhandling.gateway.DefaultCommandGateway.builder()
                .commandBus(settlementCommandBus)
                .build();
    }
}