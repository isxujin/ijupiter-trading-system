package net.ijupiter.trading.web.tradingengine.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Web模块自动配置类
 * 使用组件扫描代替手动Bean定义，更加简洁和现代化
 */
@Configuration
@ComponentScan(basePackages = {
    "net.ijupiter.trading.web.tradingengine.controllers"
})
public class TradingEngineAutoConfiguration {

}