package net.ijupiter.trading.web.query.configs;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 查询Web自动配置
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "net.ijupiter.trading.web.query")
public class QueryWebAutoConfiguration {
}