package net.ijupiter.trading.api.engine.events;

import lombok.Getter;
import net.ijupiter.trading.api.engine.enums.OrderSide;
import net.ijupiter.trading.api.engine.enums.OrderStatus;
import net.ijupiter.trading.api.engine.enums.OrderType;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单创建事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class OrderCreatedEvent {

    //@TargetAggregateIdentifier
    private final String orderId;
    
    private final String accountId;
    private final String productId;
    private final OrderSide side;
    private final OrderType type;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final OrderStatus status;
    private final LocalDateTime createTime;
    private final Long timestamp;

    public OrderCreatedEvent(String orderId, String accountId, String productId, 
                           OrderSide side, OrderType type, BigDecimal price, 
                           BigDecimal quantity, OrderStatus status) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.productId = productId;
        this.side = side;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.createTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}