package net.ijupiter.trading.core.engine.configs;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 交易引擎配置（适配 Axon 5.x，移除无效的 transactionManager 配置）
 */
@Configuration
public class TradingEngineConfig {

    /**
     * Axon 5.x 分布式命令总线（使用默认配置，无需手动指定事务管理器）
     * 事务管理器由 Axon 自动从 Spring 上下文加载
     */
//    @Bean
//    public CommandBus commandBus() {
//        // 5.x 简化构建，无需 transactionManager 方法
//        return DistributedCommandBus.builder().build();
//    }
//    @Bean
//    @Primary // 确保优先使用该 Bean，覆盖其他 CommandBus 配置
//    public CommandBus commandBus() {
//        // 单机环境：SimpleCommandBus 是最简单的实现，无额外依赖
//        return SimpleCommandBus.builder()
//                .build();
//    }
}