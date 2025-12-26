package net.ijupiter.trading.core.securities.aggregates;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import net.ijupiter.trading.api.securities.commands.CreateSecuritiesAccountCommand;
import net.ijupiter.trading.api.securities.commands.TransferSecuritiesCommand;
import net.ijupiter.trading.api.securities.events.SecuritiesAccountCreatedEvent;
import net.ijupiter.trading.api.securities.events.SecuritiesTransferredEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * 证券账户聚合根
 */
@Aggregate
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SecuritiesAccount {
    
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
     * 证券总市值
     */
    private BigDecimal totalMarketValue;
    
    /**
     * 总资产(证券市值+资金余额)
     */
    private BigDecimal totalAssets;
    
    /**
     * 冻结证券市值
     */
    private BigDecimal frozenMarketValue;
    
    /**
     * 可用证券市值
     */
    private BigDecimal availableMarketValue;
    
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
     * 创建证券账户命令处理器
     */
    @CommandHandler
    public SecuritiesAccount(CreateSecuritiesAccountCommand command) {
        log.info("处理创建证券账户命令: {}", command);
        
        // 生成账户编号
        String accountCode = generateAccountCode();
        
        // 应用证券账户创建事件
        SecuritiesAccountCreatedEvent event = SecuritiesAccountCreatedEvent.builder()
                .accountCode(accountCode)
                .customerId(command.getCustomerId())
                .customerCode(command.getCustomerCode())
                .accountName(command.getAccountName())
                .initialFunds(command.getInitialFunds() != null ? command.getInitialFunds() : BigDecimal.ZERO)
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .remark(command.getRemark())
                .build();
                
        apply(event);
    }
    
    /**
     * 证券转托管命令处理器
     */
    @CommandHandler
    public void handle(TransferSecuritiesCommand command) {
        log.info("处理证券转托管命令: {}", command);
        
        // 检查账户状态
        if (status != 1) {
            throw new IllegalStateException("账户状态异常，无法转托管");
        }
        
        // 应用证券转托管事件
        SecuritiesTransferredEvent event = SecuritiesTransferredEvent.builder()
                .transferCode(command.getTransferCode())
                .accountCode(command.getAccountCode())
                .securityCode(command.getSecurityCode())
                .securityName(command.getSecurityName())
                .quantity(command.getQuantity())
                .transferType(command.getTransferType())
                .toBrokerId(command.getToBrokerId())
                .toBrokerName(command.getToBrokerName())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .remark(command.getRemark())
                .build();
                
        apply(event);
    }
    
    /**
     * 证券账户创建事件处理器
     */
    @EventSourcingHandler
    public void on(SecuritiesAccountCreatedEvent event) {
        log.debug("应用证券账户创建事件: {}", event);
        
        this.accountCode = event.getAccountCode();
        this.customerId = event.getCustomerId();
        this.customerCode = event.getCustomerCode();
        this.accountName = event.getAccountName();
        this.totalMarketValue = BigDecimal.ZERO;
        this.totalAssets = event.getInitialFunds() != null ? event.getInitialFunds() : BigDecimal.ZERO;
        this.frozenMarketValue = BigDecimal.ZERO;
        this.availableMarketValue = this.totalMarketValue;
        this.status = 1; // 正常状态
        this.openDate = event.getEventTime();
        this.updateTime = event.getEventTime();
        this.remark = event.getRemark();
    }
    
    /**
     * 证券转托管事件处理器
     */
    @EventSourcingHandler
    public void on(SecuritiesTransferredEvent event) {
        log.debug("应用证券转托管事件: {}", event);
        
        // 更新账户信息
        if (event.getTransferType() == 1) { // 转出
            // 减少可用市值
            this.frozenMarketValue = this.frozenMarketValue.add(event.getQuantity());
            this.availableMarketValue = this.totalMarketValue.subtract(this.frozenMarketValue);
        } else { // 转入
            // 解冻市值
            this.frozenMarketValue = this.frozenMarketValue.subtract(event.getQuantity());
            this.availableMarketValue = this.totalMarketValue.subtract(this.frozenMarketValue);
        }
        
        this.updateTime = event.getEventTime();
    }
    
    /**
     * 生成账户编号
     */
    private String generateAccountCode() {
        // 简单生成规则: S + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "S" + suffix + random;
    }
}