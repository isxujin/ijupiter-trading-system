package net.ijupiter.trading.core.customer.aggregates;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.commands.CreateFundAccountCommand;
import net.ijupiter.trading.api.customer.enums.FundAccountType;
import net.ijupiter.trading.api.customer.events.FundAccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户聚合（合并银行卡信息）
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Aggregate
@NoArgsConstructor
@Slf4j
public class FundAccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private String accountCode;
    private String customerId;
    private String accountName;
    private AccountStatus status;
    private FundAccountType accountType;
    
    // 银行卡信息
    private String bankCardNumber;
    private String bankCode;
    private String bankName;
    private String holderName;
    private String currency;
    
    // 余额信息
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private BigDecimal frozenAmount;
    private BigDecimal totalBalance;
    private BigDecimal dailyDepositAmount;
    private BigDecimal dailyWithdrawAmount;
    private BigDecimal dailyNetAmount;
    
    private LocalDateTime createTime;
    private Long version;

    /**
     * 创建资金账户命令处理器
     * 
     * @param command 创建资金账户命令
     */
    @CommandHandler
    public FundAccountAggregate(CreateFundAccountCommand command) {
        log.info("处理创建资金账户命令: {}", command.getAccountId());
        
        FundAccountCreatedEvent event = new FundAccountCreatedEvent(
                command.getAccountId(),
                command.getCustomerId(),
                command.getAccountName(),
                command.getAccountType(),
                AccountStatus.NORMAL,
                command.getBankCardNumber(),
                command.getBankCode(),
                command.getBankName(),
                command.getHolderName(),
                command.getCurrency()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 资金账户创建事件处理器
     */
    @EventSourcingHandler
    public void on(FundAccountCreatedEvent event) {
        log.info("处理资金账户创建事件: {}", event.getAccountId());
        
        this.accountId = event.getAccountId();
        this.customerId = event.getCustomerId();
        this.accountName = event.getAccountName();
        this.status = event.getStatus();
        this.accountType = event.getAccountType();
        
        // 银行卡信息
        this.bankCardNumber = event.getBankCardNumber();
        this.bankCode = event.getBankCode();
        this.bankName = event.getBankName();
        this.holderName = event.getHolderName();
        this.currency = event.getCurrency();
        
        // 余额信息初始化
        this.balance = BigDecimal.ZERO;
        this.availableBalance = BigDecimal.ZERO;
        this.frozenAmount = BigDecimal.ZERO;
        this.totalBalance = BigDecimal.ZERO;
        this.dailyDepositAmount = BigDecimal.ZERO;
        this.dailyWithdrawAmount = BigDecimal.ZERO;
        this.dailyNetAmount = BigDecimal.ZERO;
        
        this.createTime = event.getCreateTime();
        this.version = 0L;
    }
}