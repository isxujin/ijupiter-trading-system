package net.ijupiter.trading.core.settlement.aggregates;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import net.ijupiter.trading.api.settlement.commands.CreateSettlementCommand;
import net.ijupiter.trading.api.settlement.commands.ProcessSettlementCommand;
import net.ijupiter.trading.api.settlement.commands.CompleteSettlementCommand;
import net.ijupiter.trading.api.settlement.events.SettlementCreatedEvent;
import net.ijupiter.trading.api.settlement.events.SettlementProcessedEvent;
import net.ijupiter.trading.api.settlement.events.SettlementCompletedEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * 清算聚合根
 */
@Aggregate
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Settlement {
    
    /**
     * 清算编号(聚合标识符)
     */
    @AggregateIdentifier
    private String settlementCode;
    
    /**
     * 清算类型
     */
    private Integer settlementType;
    
    /**
     * 交易编号
     */
    private String tradeCode;
    
    /**
     * 买方客户ID
     */
    private Long buyerCustomerId;
    
    /**
     * 卖方客户ID
     */
    private Long sellerCustomerId;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 清算数量
     */
    private BigDecimal quantity;
    
    /**
     * 清算价格
     */
    private BigDecimal price;
    
    /**
     * 清算金额
     */
    private BigDecimal amount;
    
    /**
     * 手续费
     */
    private BigDecimal fee;
    
    /**
     * 印花税
     */
    private BigDecimal tax;
    
    /**
     * 清算状态
     */
    private Integer status;
    
    /**
     * 清算日期
     */
    private LocalDateTime settlementDate;
    
    /**
     * 清算确认日期
     */
    private LocalDateTime confirmDate;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建清算命令处理器
     */
    @CommandHandler
    public Settlement(CreateSettlementCommand command) {
        log.info("处理创建清算命令: {}", command);
        
        // 生成清算编号
        String settlementCode = generateSettlementCode();
        
        // 计算清算金额
        BigDecimal amount = command.getQuantity().multiply(command.getPrice());
        
        // 计算手续费（简单固定费率）
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(0.0003)); // 0.03%
        
        // 计算印花税（卖出时征收）
        BigDecimal tax = command.getSettlementType() == 2 ? amount.multiply(BigDecimal.valueOf(0.001)) : BigDecimal.ZERO; // 0.1%
        
        // 应用清算创建事件
        SettlementCreatedEvent event = SettlementCreatedEvent.builder()
                .settlementCode(settlementCode)
                .settlementType(command.getSettlementType())
                .tradeCode(command.getTradeCode())
                .buyerCustomerId(command.getBuyerCustomerId())
                .sellerCustomerId(command.getSellerCustomerId())
                .securityCode(command.getSecurityCode())
                .securityName(command.getSecurityName())
                .quantity(command.getQuantity())
                .price(command.getPrice())
                .amount(amount)
                .fee(fee)
                .tax(tax)
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .remark(command.getRemark())
                .build();
                
        apply(event);
    }
    
    /**
     * 处理清算命令处理器
     */
    @CommandHandler
    public void handle(ProcessSettlementCommand command) {
        log.info("处理清算命令: {}", command);
        
        // 检查清算状态
        if (status != 1) {
            throw new IllegalStateException("清算状态异常，无法处理");
        }
        
        // 应用清算处理事件
        SettlementProcessedEvent event = SettlementProcessedEvent.builder()
                .settlementCode(command.getSettlementCode())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .build();
                
        apply(event);
    }
    
    /**
     * 完成清算命令处理器
     */
    @CommandHandler
    public void handle(CompleteSettlementCommand command) {
        log.info("处理完成清算命令: {}", command);
        
        // 检查清算状态
        if (status != 2) {
            throw new IllegalStateException("清算状态异常，无法完成");
        }
        
        // 应用清算完成事件
        SettlementCompletedEvent event = SettlementCompletedEvent.builder()
                .settlementCode(command.getSettlementCode())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .build();
                
        apply(event);
    }
    
    /**
     * 清算创建事件处理器
     */
    @EventSourcingHandler
    public void on(SettlementCreatedEvent event) {
        log.debug("应用清算创建事件: {}", event);
        
        this.settlementCode = event.getSettlementCode();
        this.settlementType = event.getSettlementType();
        this.tradeCode = event.getTradeCode();
        this.buyerCustomerId = event.getBuyerCustomerId();
        this.sellerCustomerId = event.getSellerCustomerId();
        this.securityCode = event.getSecurityCode();
        this.securityName = event.getSecurityName();
        this.quantity = event.getQuantity();
        this.price = event.getPrice();
        this.amount = event.getAmount();
        this.fee = event.getFee();
        this.tax = event.getTax();
        this.status = 1; // 待清算
        this.settlementDate = event.getEventTime();
        this.confirmDate = null;
        this.remark = event.getRemark();
    }
    
    /**
     * 清算处理事件处理器
     */
    @EventSourcingHandler
    public void on(SettlementProcessedEvent event) {
        log.debug("应用清算处理事件: {}", event);
        
        this.status = 2; // 清算中
    }
    
    /**
     * 清算完成事件处理器
     */
    @EventSourcingHandler
    public void on(SettlementCompletedEvent event) {
        log.debug("应用清算完成事件: {}", event);
        
        this.status = 3; // 已清算
        this.confirmDate = event.getEventTime();
    }
    
    /**
     * 生成清算编号
     */
    private String generateSettlementCode() {
        // 简单生成规则: S + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "S" + suffix + random;
    }
}