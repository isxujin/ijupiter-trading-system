package net.ijupiter.trading.api.settlement.events;

import lombok.Getter;
import net.ijupiter.trading.api.settlement.enums.SettlementStatus;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 结算开始事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class SettlementStartedEvent {

    //@TargetAggregateIdentifier
    private final String settlementId;
    
    private final LocalDate settlementDate;
    private final SettlementStatus status;
    private final LocalDateTime startTime;
    private final Long timestamp;

    public SettlementStartedEvent(String settlementId, LocalDate settlementDate) {
        this.settlementId = settlementId;
        this.settlementDate = settlementDate;
        this.status = SettlementStatus.IN_PROGRESS;
        this.startTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}