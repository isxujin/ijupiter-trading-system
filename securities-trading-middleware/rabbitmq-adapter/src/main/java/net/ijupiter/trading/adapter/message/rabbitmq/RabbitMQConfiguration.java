package net.ijupiter.trading.adapter.message.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    @Value("${trading.rabbitmq.exchange.events:trading.events}")
    private String eventsExchange;
    
    @Value("${trading.rabbitmq.exchange.commands:trading.commands}")
    private String commandsExchange;
    
    @Value("${trading.rabbitmq.exchange.queries:trading.queries}")
    private String queriesExchange;
    
    @Value("${trading.rabbitmq.exchange.delayed:trading.delayed}")
    private String delayedExchange;

    /**
     * 消息转换器
     * 
     * @return 消息转换器
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitMQ模板
     * 
     * @param connectionFactory 连接工厂
     * @return RabbitMQ模板
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        // 开启发送确认
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                // 消息发送成功
            } else {
                // 消息发送失败
            }
        });
        // 开启返回确认
        rabbitTemplate.setReturnsCallback(returned -> {
            // 消息路由失败
        });
        return rabbitTemplate;
    }

    /**
     * 事件交换机
     * 
     * @return 事件交换机
     */
    @Bean
    public TopicExchange eventsExchange() {
        return ExchangeBuilder.topicExchange(eventsExchange)
                .durable(true)
                .build();
    }

    /**
     * 命令交换机
     * 
     * @return 命令交换机
     */
    @Bean
    public DirectExchange commandsExchange() {
        return ExchangeBuilder.directExchange(commandsExchange)
                .durable(true)
                .build();
    }

    /**
     * 查询交换机
     * 
     * @return 查询交换机
     */
    @Bean
    public DirectExchange queriesExchange() {
        return ExchangeBuilder.directExchange(queriesExchange)
                .durable(true)
                .build();
    }

    /**
     * 延迟交换机
     * 
     * @return 延迟交换机
     */
//    @Bean
//    public CustomExchange delayedExchange() {
//        return ExchangeBuilder.customExchange(delayedExchange, "x-delayed-message", true)
//                .argument("x-delayed-type", "topic")
//                .build();
//    }

    /**
     * 订单事件队列
     * 
     * @return 订单事件队列
     */
    @Bean
    public Queue orderEventQueue() {
        return QueueBuilder.durable("trading.order.event")
                .build();
    }

    /**
     * 订单事件队列绑定
     * 
     * @return 订单事件队列绑定
     */
    @Bean
    public Binding orderEventBinding() {
        return BindingBuilder.bind(orderEventQueue())
                .to(eventsExchange())
                .with("order.event.*");
    }

    /**
     * 订单命令队列
     * 
     * @return 订单命令队列
     */
    @Bean
    public Queue orderCommandQueue() {
        return QueueBuilder.durable("trading.order.command")
                .build();
    }

    /**
     * 订单命令队列绑定
     * 
     * @return 订单命令队列绑定
     */
    @Bean
    public Binding orderCommandBinding() {
        return BindingBuilder.bind(orderCommandQueue())
                .to(commandsExchange())
                .with("order.command");
    }

    /**
     * 账户事件队列
     * 
     * @return 账户事件队列
     */
    @Bean
    public Queue accountEventQueue() {
        return QueueBuilder.durable("trading.account.event")
                .build();
    }

    /**
     * 账户事件队列绑定
     * 
     * @return 账户事件队列绑定
     */
    @Bean
    public Binding accountEventBinding() {
        return BindingBuilder.bind(accountEventQueue())
                .to(eventsExchange())
                .with("account.event.*");
    }

    /**
     * 资金事件队列
     * 
     * @return 资金事件队列
     */
    @Bean
    public Queue fundEventQueue() {
        return QueueBuilder.durable("trading.fund.event")
                .build();
    }

    /**
     * 资金事件队列绑定
     * 
     * @return 资金事件队列绑定
     */
    @Bean
    public Binding fundEventBinding() {
        return BindingBuilder.bind(fundEventQueue())
                .to(eventsExchange())
                .with("fund.event.*");
    }
}