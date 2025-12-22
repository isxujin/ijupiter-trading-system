package net.ijupiter.trading.boot.web.investment.configs;

import net.ijupiter.trading.web.common.configs.CommonConfig;
import net.ijupiter.trading.web.common.configs.ThymeleafConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Common终端自动配置类
 * 自动配置公共资源、控制器和模板解析器
 */
@AutoConfiguration
@ComponentScan(basePackages = "net.ijupiter.trading.web.investment")
@EnableConfigurationProperties
public class InvestmentAutoConfiguration {
}