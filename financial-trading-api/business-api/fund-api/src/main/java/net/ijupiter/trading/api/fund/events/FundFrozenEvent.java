package net.ijupiter.trading.api.fund.events;

import lombok.Getter;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金冻结事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class FundFrozenEvent {

    //@TargetAggregateIdentifier
    private final String fundAccountId;
    
    private final String transactionId;
    private final BigDecimal amount;
    private final BigDecimal balance;
    private final BigDecimal frozenAmount;
    private final String description;
    private final LocalDateTime transactionTime;
    private final Long timestamp;

    public FundFrozenEvent(String fundAccountId, String transactionId, BigDecimal amount, 
                           BigDecimal balance, BigDecimal frozenAmount, String description) {
        this.fundAccountId = fundAccountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.balance = balance;
        this.frozenAmount = frozenAmount;
        this.description = description;
        this.transactionTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}