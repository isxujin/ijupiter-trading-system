package net.ijupiter.trading.core.customer.configs;

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

import net.ijupiter.trading.core.customer.aggregates.Customer;

/**
 * 客户模块配置类
 */
@Configuration
public class CustomerConfig {
    
    /**
     * 客户聚合仓储
     */
    @Bean
    public Repository<Customer> customerRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(Customer.class)
                .eventStore(eventStore)
                .build();
    }
//
//    /**
//     * 内存事件存储引擎（仅用于演示）
//     */
//    @Bean
//    @Primary
//    public EventStorageEngine eventStorageEngine() {
//        return new InMemoryEventStorageEngine();
//    }
    
    /**
     * 事件存储
     */
    @Bean
    public EventStore eventStore(EventStorageEngine eventStorageEngine) {
        return org.axonframework.eventsourcing.eventstore.EmbeddedEventStore.builder()
                .storageEngine(eventStorageEngine)
                .build();
    }
    
    /**
     * 命令总线
     */
    @Bean
    public CommandBus commandBus() {
        return SimpleCommandBus.builder().build();
    }
    
    /**
     * 命令网关
     */
    @Bean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return org.axonframework.commandhandling.gateway.DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .build();
    }
}