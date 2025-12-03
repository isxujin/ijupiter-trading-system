package net.ijupiter.trading.boot.service.test.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.axonframework.queryhandling.DefaultQueryGateway;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SimpleQueryBus;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.TimeUnit;

/**
 * 核心：所有 Bean 加 @Primary，确保唯一且优先被注入
 */
@Configuration
public class AxonConfig {

    // ======================== 1. 基础 Bean：EntityManagerProvider （必须优先注册） ==================================
    @Bean
    public EntityManagerProvider entityManagerProvider(EntityManager entityManager) {
        return () -> entityManager;
    }

    // ======================== 2. 基础 Bean：TransactionManager （必须优先注册） ==================================
    @Bean
    public SpringTransactionManager axonTransactionManager(PlatformTransactionManager platformTransactionManager) {
        return new SpringTransactionManager(platformTransactionManager);
    }

    // ======================== 3. 核心：JacksonSerializer（替代 XStream，消除安全警告） ========================
    @Bean
    public Serializer eventSerializer() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return JacksonSerializer.builder()
                .objectMapper(objectMapper)
                .build();
    }

    // ======================== 4. 核心：CommandBus 命令总线（必须加 @Primary：Spring优先注入） ========================
    @Bean
    @Primary
    public CommandBus commandBus(SpringTransactionManager axonTransactionManager) {
        return SimpleCommandBus.builder()
                .transactionManager(axonTransactionManager)
                .messageMonitor(NoOpMessageMonitor.instance())
                .build();
    }

    // ======================== 5. 核心：CommandGateway 命令网关（必须加 @Primary：Spring优先注入） ========================
    @Bean
    @Primary
    public CommandGateway commandGateway(CommandBus commandBus) {
        return DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .build();
    }

    // ======================== 6. 核心：QueryBus 查询总线（必须加 @Primary：Spring优先注入） ========================
    @Bean
    @Primary
    public QueryBus queryBus() {
        return SimpleQueryBus.builder()
                .messageMonitor(NoOpMessageMonitor.instance())
                .build();
    }

    // ======================== 7. 核心：QueryGateway 查询网关（必须加 @Primary：Spring优先注入） ========================
    @Bean
    @Primary
    public QueryGateway queryGateway(QueryBus queryBus) {
        return DefaultQueryGateway.builder()
                .queryBus(queryBus)
                .build();
    }

    // ======================== 8. 核心：EventStore 事件存储   ========================
    @Bean
    public EmbeddedEventStore eventStore(
            EntityManagerProvider entityManagerProvider,
            Serializer eventSerializer,
            SpringTransactionManager axonTransactionManager) {
        JpaEventStorageEngine storageEngine = JpaEventStorageEngine.builder()
                .entityManagerProvider(entityManagerProvider)
                .transactionManager(axonTransactionManager)
                .eventSerializer(eventSerializer)          // 事件序列化器
                .snapshotSerializer(eventSerializer)       // 快照序列化器
                .batchSize(100)
                .explicitFlush(true)
                .gapTimeout(60000)
                .build();
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .build();
    }

    // ======================== 9. 核心：TokenStore 令牌存储   ========================
    @Bean
    public JpaTokenStore tokenStore(EntityManagerProvider entityManagerProvider, Serializer eventSerializer) {
        return JpaTokenStore.builder()
                .entityManagerProvider(entityManagerProvider)
                .serializer(eventSerializer)
                .build();
    }

    // ======================== 10. 核心：Configurer 配置   ========================
    @Bean
    @Primary
    public Configurer axonConfigurer(
            EntityManagerProvider entityManagerProvider,
            Serializer eventSerializer,
            SpringTransactionManager axonTransactionManager,
            EmbeddedEventStore eventStore,
            CommandBus commandBus,
            QueryBus queryBus) {

        Configurer configurer = DefaultConfigurer.defaultConfiguration();

        // 注册所有核心组件到 Axon 配置器
        configurer.registerComponent(SpringTransactionManager.class, c -> axonTransactionManager);
        configurer.registerComponent(Serializer.class, c -> eventSerializer);
        configurer.registerComponent(CommandBus.class, c -> commandBus);
        configurer.registerComponent(QueryBus.class, c -> queryBus);
        configurer.registerComponent(EmbeddedEventStore.class, c -> eventStore);
        configurer.registerComponent(JpaTokenStore.class, c -> tokenStore(entityManagerProvider, eventSerializer));

        // 配置事件处理器
        configurer.eventProcessing(epc ->
                epc.registerTrackingEventProcessor(
                        "net.ijupiter.trading.handler",
                        c -> eventStore,
                        c -> TrackingEventProcessorConfiguration
                                .forParallelProcessing(2)
                                .andBatchSize(50)
                                .andTokenClaimInterval(5000, TimeUnit.MILLISECONDS)
                                .andEventAvailabilityTimeout(1000, TimeUnit.MILLISECONDS)
                                .andAutoStart(true)
                                .andWorkerTerminationTimeout(5000, TimeUnit.MILLISECONDS)
                )
        );
        return configurer;
    }

    @Bean
    public org.axonframework.config.Configuration axonConfiguration(Configurer configurer) {
        org.axonframework.config.Configuration configuration = configurer.buildConfiguration();
        configuration.start();
        return configuration;
    }
}