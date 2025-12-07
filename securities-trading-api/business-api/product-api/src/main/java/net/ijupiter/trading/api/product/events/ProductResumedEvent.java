package net.ijupiter.trading.api.product.events;

import lombok.Getter;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

/**
 * 证券产品复牌事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class ProductResumedEvent {

    //@TargetAggregateIdentifier
    private final String productId;
    
    private final String reason;
    private final LocalDateTime resumeTime;
    private final Long timestamp;

    public ProductResumedEvent(String productId, String reason) {
        this.productId = productId;
        this.reason = reason;
        this.resumeTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}