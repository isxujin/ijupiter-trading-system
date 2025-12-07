package net.ijupiter.trading.api.engine.events;

import lombok.Getter;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

/**
 * 订单取消事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class OrderCancelledEvent {

    //@TargetAggregateIdentifier
    private final String orderId;
    
    private final String reason;
    private final LocalDateTime cancelTime;
    private final Long timestamp;

    public OrderCancelledEvent(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
        this.cancelTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}