package net.ijupiter.trading.core.customer.aggregates;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.commands.CreateTradingAccountCommand;
import net.ijupiter.trading.api.customer.enums.TradingAccountType;
import net.ijupiter.trading.api.customer.events.TradingAccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易账户聚合（合并交易所账号信息）
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Aggregate
@NoArgsConstructor
@Slf4j
public class TradingAccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private String accountCode;
    private String customerId;
    private String accountName;
    private AccountStatus status;
    private TradingAccountType accountType;
    
    // 交易所账号信息
    private String exchangeCode;
    private String exchangeName;
    private String exchangeAccountNumber;
    private String tradingPassword;
    private String fundPassword;
    private String apiKey;
    private String apiSecret;
    private String tradingProduct;
    
    // 持仓信息
    private BigDecimal positionShares;
    private BigDecimal availableShares;
    private BigDecimal frozenShares;
    private BigDecimal dailyBuyShares;
    private BigDecimal dailySellShares;
    private BigDecimal dailyNetShares;
    private BigDecimal totalMarketValue;
    private BigDecimal availableMarketValue;
    private BigDecimal frozenMarketValue;
    
    private LocalDateTime createTime;
    private Long version;

    /**
     * 创建交易账户命令处理器
     * 
     * @param command 创建交易账户命令
     */
    @CommandHandler
    public TradingAccountAggregate(CreateTradingAccountCommand command) {
        log.info("处理创建交易账户命令: {}", command.getAccountId());
        
        TradingAccountCreatedEvent event = new TradingAccountCreatedEvent(
                command.getAccountId(),
                command.getCustomerId(),
                command.getAccountName(),
                command.getExchangeCode(),
                command.getExchangeName(),
                command.getExchangeAccountNumber(),
                command.getAccountType(),
                AccountStatus.NORMAL,
                command.getTradingProduct(),
                command.getTradingPassword(),
                command.getFundPassword(),
                command.getApiKey(),
                command.getApiSecret()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 交易账户创建事件处理器
     */
    @EventSourcingHandler
    public void on(TradingAccountCreatedEvent event) {
        log.info("处理交易账户创建事件: {}", event.getAccountId());
        
        this.accountId = event.getAccountId();
        this.customerId = event.getCustomerId();
        this.accountName = event.getAccountName();
        this.status = event.getStatus();
        this.accountType = event.getAccountType();
        
        // 交易所账号信息
        this.exchangeCode = event.getExchangeCode();
        this.exchangeName = event.getExchangeName();
        this.exchangeAccountNumber = event.getExchangeAccountNumber();
        this.tradingPassword = event.getTradingPassword();
        this.fundPassword = event.getFundPassword();
        this.apiKey = event.getApiKey();
        this.apiSecret = event.getApiSecret();
        this.tradingProduct = event.getTradingProduct();
        
        // 持仓信息初始化
        this.positionShares = BigDecimal.ZERO;
        this.availableShares = BigDecimal.ZERO;
        this.frozenShares = BigDecimal.ZERO;
        this.dailyBuyShares = BigDecimal.ZERO;
        this.dailySellShares = BigDecimal.ZERO;
        this.dailyNetShares = BigDecimal.ZERO;
        this.totalMarketValue = BigDecimal.ZERO;
        this.availableMarketValue = BigDecimal.ZERO;
        this.frozenMarketValue = BigDecimal.ZERO;
        
        this.createTime = event.getCreateTime();
        this.version = 0L;
    }
}