package net.ijupiter.trading.spi.message;

////import org.axonframework.messaging.Message;

import org.axonframework.messaging.Message;

import java.util.concurrent.TimeUnit;

/**
 * 消息适配器服务接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public interface MessageAdapterService {

    /**
     * 发送消息
     * 
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息体
     */
    void sendMessage(String exchange, String routingKey, Object message);

    /**
     * 发送延迟消息
     * 
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息体
     * @param delayMillis 延迟时间（毫秒）
     */
    void sendDelayedMessage(String exchange, String routingKey, Object message, long delayMillis);

    /**
     * 发送持久化消息
     * 
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息体
     * @param persistent 是否持久化
     */
    void sendPersistedMessage(String exchange, String routingKey, Object message, boolean persistent);

    /**
     * 发送事件消息
     * 
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param eventMessage 事件消息
     */
    void sendEventMessage(String exchange, String routingKey, Message<?> eventMessage);

    /**
     * 发送命令消息
     * 
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param commandMessage 命令消息
     */
    void sendCommandMessage(String exchange, String routingKey, Message<?> commandMessage);

    /**
     * 发送查询消息
     * 
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param queryMessage 查询消息
     */
    void sendQueryMessage(String exchange, String routingKey, Message<?> queryMessage);
}