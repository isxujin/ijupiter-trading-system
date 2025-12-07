package net.ijupiter.trading.adapter.message.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.spi.message.MessageAdapterService;
import org.axonframework.messaging.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * RabbitMQ消息适配器
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQMessageAdapter implements MessageAdapterService {

    private final AmqpTemplate amqpTemplate;
    
    @Autowired
    private MessageConverter messageConverter;

    @Override
    public void sendMessage(String exchange, String routingKey, Object message) {
        log.debug("发送消息: exchange={}, routingKey={}, message={}", exchange, routingKey, message);
        
        try {
            amqpTemplate.convertAndSend(exchange, routingKey, message);
            log.debug("消息发送成功");
        } catch (Exception e) {
            log.error("消息发送失败: exchange={}, routingKey={}, error={}", exchange, routingKey, e.getMessage(), e);
            throw new RuntimeException("消息发送失败", e);
        }
    }

    @Override
    public void sendDelayedMessage(String exchange, String routingKey, Object message, long delayMillis) {
        log.debug("发送延迟消息: exchange={}, routingKey={}, message={}, delayMillis={}", 
                exchange, routingKey, message, delayMillis);
        
        try {
            // 创建消息
            org.springframework.amqp.core.Message amqpMessage = messageConverter.toMessage(message, new MessageProperties());
            
            // 设置延迟参数
            amqpMessage.getMessageProperties().setHeader("x-delay", delayMillis);
            
            // 发送延迟消息
            amqpTemplate.send(exchange, routingKey, amqpMessage);
            log.debug("延迟消息发送成功");
        } catch (Exception e) {
            log.error("延迟消息发送失败: exchange={}, routingKey={}, delayMillis={}, error={}", 
                    exchange, routingKey, delayMillis, e.getMessage(), e);
            throw new RuntimeException("延迟消息发送失败", e);
        }
    }

    @Override
    public void sendPersistedMessage(String exchange, String routingKey, Object message, boolean persistent) {
        log.debug("发送持久化消息: exchange={}, routingKey={}, message={}, persistent={}", 
                exchange, routingKey, message, persistent);
        
        try {
            // 创建消息
            org.springframework.amqp.core.Message amqpMessage = messageConverter.toMessage(message, new MessageProperties());
            
            // 设置持久化参数
            if (persistent) {
                amqpMessage.getMessageProperties().setDeliveryMode(org.springframework.amqp.core.MessageDeliveryMode.PERSISTENT);
            } else {
                amqpMessage.getMessageProperties().setDeliveryMode(org.springframework.amqp.core.MessageDeliveryMode.NON_PERSISTENT);
            }
            
            // 发送消息
            amqpTemplate.send(exchange, routingKey, amqpMessage);
            log.debug("持久化消息发送成功");
        } catch (Exception e) {
            log.error("持久化消息发送失败: exchange={}, routingKey={}, persistent={}, error={}", 
                    exchange, routingKey, persistent, e.getMessage(), e);
            throw new RuntimeException("持久化消息发送失败", e);
        }
    }

    @Override
    public void sendEventMessage(String exchange, String routingKey, Message<?> eventMessage) {
        log.debug("发送事件消息: exchange={}, routingKey={}, eventMessage={}", exchange, routingKey, eventMessage);
        
        try {
            // 转换Axon消息为RabbitMQ消息
            String payload = eventMessage.getPayload().toString();
            
            // 创建消息属性
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());
            
            // 添加事件元数据
            eventMessage.getMetaData().forEach((key, value) -> {
                messageProperties.setHeader(key, value);
            });
            
            // 创建消息
            org.springframework.amqp.core.Message amqpMessage = MessageBuilder
                    .withBody(payload.getBytes(StandardCharsets.UTF_8))
                    .andProperties(messageProperties)
                    .build();
            
            // 发送消息
            amqpTemplate.send(exchange, routingKey, amqpMessage);
            log.debug("事件消息发送成功");
        } catch (Exception e) {
            log.error("事件消息发送失败: exchange={}, routingKey={}, error={}", exchange, routingKey, e.getMessage(), e);
            throw new RuntimeException("事件消息发送失败", e);
        }
    }

    @Override
    public void sendCommandMessage(String exchange, String routingKey, Message<?> commandMessage) {
        log.debug("发送命令消息: exchange={}, routingKey={}, commandMessage={}", exchange, routingKey, commandMessage);
        
        try {
            // 转换Axon消息为RabbitMQ消息
            String payload = commandMessage.getPayload().toString();
            
            // 创建消息属性
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());
            
            // 添加命令元数据
            commandMessage.getMetaData().forEach((key, value) -> {
                messageProperties.setHeader(key, value);
            });
            
            // 设置命令消息特有属性
            messageProperties.setHeader("message-type", "command");
            
            // 创建消息
            org.springframework.amqp.core.Message amqpMessage = MessageBuilder
                    .withBody(payload.getBytes(StandardCharsets.UTF_8))
                    .andProperties(messageProperties)
                    .build();
            
            // 发送消息
            amqpTemplate.send(exchange, routingKey, amqpMessage);
            log.debug("命令消息发送成功");
        } catch (Exception e) {
            log.error("命令消息发送失败: exchange={}, routingKey={}, error={}", exchange, routingKey, e.getMessage(), e);
            throw new RuntimeException("命令消息发送失败", e);
        }
    }

    @Override
    public void sendQueryMessage(String exchange, String routingKey, Message<?> queryMessage) {
        log.debug("发送查询消息: exchange={}, routingKey={}, queryMessage={}", exchange, routingKey, queryMessage);
        
        try {
            // 转换Axon消息为RabbitMQ消息
            String payload = queryMessage.getPayload().toString();
            
            // 创建消息属性
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());
            
            // 添加查询元数据
            queryMessage.getMetaData().forEach((key, value) -> {
                messageProperties.setHeader(key, value);
            });
            
            // 设置查询消息特有属性
            messageProperties.setHeader("message-type", "query");
            messageProperties.setReplyTo(queryMessage.getMetaData().get("reply-to").toString());
            
            // 创建消息
            org.springframework.amqp.core.Message amqpMessage = MessageBuilder
                    .withBody(payload.getBytes(StandardCharsets.UTF_8))
                    .andProperties(messageProperties)
                    .build();
            
            // 发送消息
            amqpTemplate.send(exchange, routingKey, amqpMessage);
            log.debug("查询消息发送成功");
        } catch (Exception e) {
            log.error("查询消息发送失败: exchange={}, routingKey={}, error={}", exchange, routingKey, e.getMessage(), e);
            throw new RuntimeException("查询消息发送失败", e);
        }
    }
}