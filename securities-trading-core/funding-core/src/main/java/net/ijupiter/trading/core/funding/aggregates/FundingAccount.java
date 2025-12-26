package net.ijupiter.trading.core.funding.aggregates;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import net.ijupiter.trading.api.funding.commands.CreateFundingAccountCommand;
import net.ijupiter.trading.api.funding.commands.TransferFundingCommand;
import net.ijupiter.trading.api.funding.commands.FreezeFundingCommand;
import net.ijupiter.trading.api.funding.events.FundingAccountCreatedEvent;
import net.ijupiter.trading.api.funding.events.FundingTransferredEvent;
import net.ijupiter.trading.api.funding.events.FundingFrozenEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * 资金账户聚合根
 */
@Aggregate
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FundingAccount {
    
    /**
     * 账户编号(聚合标识符)
     */
    @AggregateIdentifier
    private String accountCode;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 客户编号
     */
    private String customerCode;
    
    /**
     * 账户名称
     */
    private String accountName;
    
    /**
     * 账户余额
     */
    private BigDecimal balance;
    
    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;
    
    /**
     * 可用余额
     */
    private BigDecimal availableBalance;
    
    /**
     * 账户状态
     */
    private Integer status;
    
    /**
     * 开户日期
     */
    private LocalDateTime openDate;
    
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建资金账户命令处理器
     */
    @CommandHandler
    public FundingAccount(CreateFundingAccountCommand command) {
        log.info("处理创建资金账户命令: {}", command);
        
        // 生成账户编号
        String accountCode = generateAccountCode();
        
        // 计算可用余额
        BigDecimal availableBalance = command.getInitialBalance() != null ? command.getInitialBalance() : BigDecimal.ZERO;
        
        // 应用资金账户创建事件
        FundingAccountCreatedEvent event = FundingAccountCreatedEvent.builder()
                .accountCode(accountCode)
                .customerId(command.getCustomerId())
                .customerCode(command.getCustomerCode())
                .accountName(command.getAccountName())
                .initialBalance(command.getInitialBalance() != null ? command.getInitialBalance() : BigDecimal.ZERO)
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .remark(command.getRemark())
                .build();
                
        apply(event);
    }
    
    /**
     * 资金转账命令处理器
     */
    @CommandHandler
    public void handle(TransferFundingCommand command) {
        log.info("处理资金转账命令: {}", command);
        
        // 检查账户状态
        if (status != 1) {
            throw new IllegalStateException("账户状态异常，无法转账");
        }
        
        // 检查余额是否充足
        if (availableBalance.compareTo(command.getAmount()) < 0) {
            throw new IllegalStateException("账户余额不足");
        }
        
        // 应用资金转账事件
        FundingTransferredEvent event = FundingTransferredEvent.builder()
                .transferCode(command.getTransferCode())
                .fromAccountCode(command.getFromAccountCode())
                .toAccountCode(command.getToAccountCode())
                .amount(command.getAmount())
                .transferType(command.getTransferType())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .remark(command.getRemark())
                .build();
                
        apply(event);
    }
    
    /**
     * 冻结资金命令处理器
     */
    @CommandHandler
    public void handle(FreezeFundingCommand command) {
        log.info("处理冻结资金命令: {}", command);
        
        // 检查账户状态
        if (status != 1) {
            throw new IllegalStateException("账户状态异常，无法冻结");
        }
        
        // 检查冻结金额是否超过可用余额
        if (availableBalance.compareTo(command.getAmount()) < 0) {
            throw new IllegalStateException("冻结金额超过可用余额");
        }
        
        // 应用资金冻结事件
        FundingFrozenEvent event = FundingFrozenEvent.builder()
                .accountCode(command.getAccountCode())
                .amount(command.getAmount())
                .reason(command.getReason())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .build();
                
        apply(event);
    }
    
    /**
     * 资金账户创建事件处理器
     */
    @EventSourcingHandler
    public void on(FundingAccountCreatedEvent event) {
        log.debug("应用资金账户创建事件: {}", event);
        
        this.accountCode = event.getAccountCode();
        this.customerId = event.getCustomerId();
        this.customerCode = event.getCustomerCode();
        this.accountName = event.getAccountName();
        this.balance = event.getInitialBalance() != null ? event.getInitialBalance() : BigDecimal.ZERO;
        this.frozenAmount = BigDecimal.ZERO;
        this.availableBalance = this.balance;
        this.status = 1; // 正常状态
        this.openDate = event.getEventTime();
        this.updateTime = event.getEventTime();
        this.remark = event.getRemark();
    }
    
    /**
     * 资金转账事件处理器
     */
    @EventSourcingHandler
    public void on(FundingTransferredEvent event) {
        log.debug("应用资金转账事件: {}", event);
        
        // 减少转出账户余额
        this.balance = this.balance.subtract(event.getAmount());
        this.frozenAmount = this.frozenAmount.add(event.getAmount());
        this.availableBalance = this.balance.subtract(this.frozenAmount);
        this.updateTime = event.getEventTime();
    }
    
    /**
     * 资金冻结事件处理器
     */
    @EventSourcingHandler
    public void on(FundingFrozenEvent event) {
        log.debug("应用资金冻结事件: {}", event);
        
        this.frozenAmount = this.frozenAmount.add(event.getAmount());
        this.availableBalance = this.balance.subtract(this.frozenAmount);
        this.updateTime = event.getEventTime();
    }
    
    /**
     * 生成账户编号
     */
    private String generateAccountCode() {
        // 简单生成规则: F + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "F" + suffix + random;
    }
}