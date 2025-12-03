package net.ijupiter.trading.boot.service.allinone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
public class QueryConfig {
    
}