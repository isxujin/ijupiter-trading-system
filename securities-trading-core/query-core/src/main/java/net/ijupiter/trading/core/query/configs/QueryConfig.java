package net.ijupiter.trading.core.query.configs;

import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SimpleQueryBus;
import org.axonframework.queryhandling.LoggingQueryInvocationErrorHandler;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 查询模块配置
 */
@Configuration
public class QueryConfig {

}