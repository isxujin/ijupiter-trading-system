package net.ijupiter.trading.api.fund.events;

import lombok.Getter;
import net.ijupiter.trading.api.fund.enums.DepositType;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金入金事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class FundDepositedEvent {

    //@TargetAggregateIdentifier
    private final String fundAccountId;
    
    private final String transactionId;
    private final BigDecimal amount;
    private final BigDecimal balance;
    private final DepositType depositType;
    private final String description;
    private final LocalDateTime transactionTime;
    private final Long timestamp;

    public FundDepositedEvent(String fundAccountId, String transactionId, BigDecimal amount, 
                             BigDecimal balance, DepositType depositType, String description) {
        this.fundAccountId = fundAccountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.balance = balance;
        this.depositType = depositType;
        this.description = description;
        this.transactionTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}