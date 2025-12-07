package net.ijupiter.trading.api.fund.events;

import lombok.Getter;
import net.ijupiter.trading.api.fund.enums.WithdrawType;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金出金事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class FundWithdrawnEvent {

    //@TargetAggregateIdentifier
    private final String fundAccountId;
    
    private final String transactionId;
    private final BigDecimal amount;
    private final BigDecimal balance;
    private final WithdrawType withdrawType;
    private final String description;
    private final LocalDateTime transactionTime;
    private final Long timestamp;

    public FundWithdrawnEvent(String fundAccountId, String transactionId, BigDecimal amount, 
                              BigDecimal balance, WithdrawType withdrawType, String description) {
        this.fundAccountId = fundAccountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.balance = balance;
        this.withdrawType = withdrawType;
        this.description = description;
        this.transactionTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}