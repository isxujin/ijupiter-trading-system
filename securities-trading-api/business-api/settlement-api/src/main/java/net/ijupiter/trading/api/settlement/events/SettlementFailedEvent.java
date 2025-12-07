package net.ijupiter.trading.api.settlement.events;

import lombok.Getter;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

/**
 * 结算失败事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class SettlementFailedEvent {

    //@TargetAggregateIdentifier
    private final String settlementId;
    
    private final String errorMessage;
    private final LocalDateTime failedTime;
    private final Long timestamp;

    public SettlementFailedEvent(String settlementId, String errorMessage) {
        this.settlementId = settlementId;
        this.errorMessage = errorMessage;
        this.failedTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}