package net.ijupiter.trading.api.engine.events;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易执行事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class TradeExecutedEvent {

    private final String tradeId;
    private final String orderId;
    private final String buyOrderId;
    private final String sellOrderId;
    private final String productId;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final BigDecimal amount;
    private final LocalDateTime tradeTime;
    private final Long timestamp;

    public TradeExecutedEvent(String tradeId, String orderId, String buyOrderId, 
                             String sellOrderId, String productId, BigDecimal price, 
                             BigDecimal quantity, BigDecimal amount) {
        this.tradeId = tradeId;
        this.orderId = orderId;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.tradeTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}