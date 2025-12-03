package net.ijupiter.trading.api.product.events;

import lombok.Getter;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

/**
 * 证券产品退市事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class ProductDelistedEvent {

    //@TargetAggregateIdentifier
    private final String productId;
    
    private final String reason;
    private final LocalDateTime delistTime;
    private final Long timestamp;

    public ProductDelistedEvent(String productId, String reason) {
        this.productId = productId;
        this.reason = reason;
        this.delistTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}