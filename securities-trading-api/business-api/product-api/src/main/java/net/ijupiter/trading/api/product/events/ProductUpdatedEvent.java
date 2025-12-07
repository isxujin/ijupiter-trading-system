package net.ijupiter.trading.api.product.events;

import lombok.Getter;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

/**
 * 证券产品更新事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class ProductUpdatedEvent {

    //@TargetAggregateIdentifier
    private final String productId;
    
    private final String productName;
    private final LocalDateTime updateTime;
    private final Long timestamp;

    public ProductUpdatedEvent(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
        this.updateTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}