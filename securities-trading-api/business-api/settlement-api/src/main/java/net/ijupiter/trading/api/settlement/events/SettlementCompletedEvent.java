package net.ijupiter.trading.api.settlement.events;

import lombok.Getter;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

/**
 * 结算完成事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class SettlementCompletedEvent {

    //@TargetAggregateIdentifier
    private final String settlementId;
    
    private final LocalDateTime completedTime;
    private final Long timestamp;

    public SettlementCompletedEvent(String settlementId) {
        this.settlementId = settlementId;
        this.completedTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}