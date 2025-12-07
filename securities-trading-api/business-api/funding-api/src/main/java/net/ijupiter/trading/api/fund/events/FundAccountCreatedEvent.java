package net.ijupiter.trading.api.fund.events;

import lombok.Getter;
import net.ijupiter.trading.api.fund.enums.FundAccountStatus;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户创建事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class FundAccountCreatedEvent {

    //@TargetAggregateIdentifier
    private final String fundAccountId;
    
    private final String accountId;
    private final BigDecimal balance;
    private final BigDecimal frozenAmount;
    private final FundAccountStatus status;
    private final LocalDateTime createTime;
    private final Long timestamp;

    public FundAccountCreatedEvent(String fundAccountId, String accountId, BigDecimal balance, 
                                  BigDecimal frozenAmount, FundAccountStatus status) {
        this.fundAccountId = fundAccountId;
        this.accountId = accountId;
        this.balance = balance;
        this.frozenAmount = frozenAmount;
        this.status = status;
        this.createTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}