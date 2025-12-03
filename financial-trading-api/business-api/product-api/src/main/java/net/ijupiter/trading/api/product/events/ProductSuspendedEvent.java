package net.ijupiter.trading.api.product.events;

import lombok.Getter;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

/**
 * 证券产品停牌事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class ProductSuspendedEvent {

    //@TargetAggregateIdentifier
    private final String productId;
    
    private final String reason;
    private final LocalDateTime suspendTime;
    private final Long timestamp;

    public ProductSuspendedEvent(String productId, String reason) {
        this.productId = productId;
        this.reason = reason;
        this.suspendTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}