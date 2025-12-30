package net.ijupiter.trading.boot.service.allinone.configs;

import org.axonframework.monitoring.NoOpMessageMonitor;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SimpleQueryBus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 查询模块配置
 * 
 * @author ijupiter
 */
@Configuration
@EnableTransactionManagement
//@EnableConfigurationProperties(QueryProperties.class)
@Import({AxonConfig.class})
public class QueryBootConfig {

    /**
     * 查询总线配置
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public QueryBus queryBus() {
        return SimpleQueryBus.builder()
                .messageMonitor(NoOpMessageMonitor.instance())
                .build();
    }

    /**
     * 查询网关配置
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public QueryGateway queryGateway(QueryBus queryBus) {
        return org.axonframework.queryhandling.DefaultQueryGateway.builder()
                .queryBus(queryBus)
                .build();
    }
}