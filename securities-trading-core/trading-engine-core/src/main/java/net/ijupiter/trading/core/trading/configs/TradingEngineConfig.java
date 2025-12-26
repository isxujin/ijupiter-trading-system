package net.ijupiter.trading.core.trading.configs;

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

import net.ijupiter.trading.core.trading.aggregates.TradingEngine;

/**
 * 交易引擎模块配置类
 */
@Configuration
public class TradingEngineConfig {
    
    /**
     * 交易引擎聚合仓储
     */
    @Bean
    public Repository<TradingEngine> tradingEngineRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(TradingEngine.class)
                .eventStore(eventStore)
                .build();
    }
    
    /**
     * 事件存储
     */
    @Bean
    public EventStore tradingEngineEventStore(EventStorageEngine tradingEngineEventStorageEngine) {
        return org.axonframework.eventsourcing.eventstore.EmbeddedEventStore.builder()
                .storageEngine(tradingEngineEventStorageEngine)
                .build();
    }
    
    /**
     * 命令总线
     */
    @Bean
    public CommandBus tradingEngineCommandBus() {
        return SimpleCommandBus.builder().build();
    }
    
    /**
     * 命令网关
     */
    @Bean
    public CommandGateway tradingEngineCommandGateway(CommandBus tradingEngineCommandBus) {
        return org.axonframework.commandhandling.gateway.DefaultCommandGateway.builder()
                .commandBus(tradingEngineCommandBus)
                .build();
    }
}