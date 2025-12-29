package net.ijupiter.trading.boot.service.allinone.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 完整的Axon配置，提供必要的事件存储和TokenStore
 */
@Configuration
@Import(AxonAutoConfiguration.class)
public class AxonConfig {
    
    static {
        // 静态初始化块，禁用Axon更新检查
        System.setProperty("axon.update-check.enabled", "false");
    }

    // ======================== 1. 基础 Bean：EntityManagerProvider ========================
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public EntityManagerProvider entityManagerProvider(EntityManager entityManager) {
        return () -> entityManager;
    }

    // ======================== 2. 核心：Jackson序列化器 ========================
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public Serializer serializer() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return JacksonSerializer.builder()
                .objectMapper(objectMapper)
                .build();
    }

    // ======================== 3. 核心：Spring事务管理器 ========================
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public SpringTransactionManager springTransactionManager(PlatformTransactionManager platformTransactionManager) {
        return new SpringTransactionManager(platformTransactionManager);
    }

    // ======================== 4. 核心：TokenStore 令牌存储 ========================
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public JpaTokenStore tokenStore(EntityManagerProvider entityManagerProvider, Serializer serializer) {
        return JpaTokenStore.builder()
                .entityManagerProvider(entityManagerProvider)
                .serializer(serializer)
                .build();
    }

    // ======================== 5. 核心：JPA事件存储引擎 ========================
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public JpaEventStorageEngine eventStorageEngine(
            EntityManagerProvider entityManagerProvider,
            Serializer serializer,
            SpringTransactionManager transactionManager) {
        return JpaEventStorageEngine.builder()
                .entityManagerProvider(entityManagerProvider)
                .transactionManager(transactionManager)
                .eventSerializer(serializer)
                .snapshotSerializer(serializer)
                .build();
    }

    // ======================== 6. 核心：事件存储 ========================
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public EmbeddedEventStore eventStore(JpaEventStorageEngine eventStorageEngine) {
        return EmbeddedEventStore.builder()
                .storageEngine(eventStorageEngine)
                .build();
    }
}