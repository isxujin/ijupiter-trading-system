package net.ijupiter.trading.boot.service.allinone.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MessageConfig {
    // ========== 1. 初始化 RabbitMQ 交换机/队列（命令传输） ==========
    @Value("${axon.amqp.exchange:axon-command-exchange}")
    private String commandExchange;
    @Value("${axon.amqp.queue:axon-command-queue}")
    private String commandQueue;
    @Value("${axon.amqp.routing-key:axon.command}")
    private String routingKey;

    @Bean
    public Exchange commandExchange() {
        // 持久化直连交换机（生产环境推荐）
        Exchange result = ExchangeBuilder
                .directExchange(commandExchange)
                .durable(true)
                .build();
        log.info("MessageExchange initialized successfully:{}",result.toString());
        return result;
    }

    @Bean
    public Queue commandQueue() {
        // 持久化队列，不自动删除
        Queue result = QueueBuilder
                .durable(commandQueue)
//                .autoDelete()
                .build();
        log.info("MessageQueue initialized successfully:{}",result.toString());
        return result;
    }

    @Bean
    public Binding commandBinding() {
        // 绑定交换机与队列
        Binding result = BindingBuilder
                .bind(commandQueue())
                .to(commandExchange())
                .with(routingKey)
                .noargs();
        log.info("MessageBinding initialized successfully:{}",result.toString());
        return result;
    }
}
