package net.ijupiter.trading.core.trading.aggregates;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import net.ijupiter.trading.api.trading.commands.CreateTradeCommand;
import net.ijupiter.trading.api.trading.commands.MatchTradeCommand;
import net.ijupiter.trading.api.trading.commands.ExecuteTradeCommand;
import net.ijupiter.trading.api.trading.commands.CancelTradeCommand;
import net.ijupiter.trading.api.trading.events.TradeCreatedEvent;
import net.ijupiter.trading.api.trading.events.TradeMatchedEvent;
import net.ijupiter.trading.api.trading.events.TradeExecutedEvent;
import net.ijupiter.trading.api.trading.events.TradeCancelledEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * 交易引擎聚合根
 */
@Aggregate
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TradingEngine {
    
    /**
     * 交易编号(聚合标识符)
     */
    @AggregateIdentifier
    private String tradeCode;
    
    /**
     * 订单编号
     */
    private String orderCode;
    
    /**
     * 交易类型
     */
    private Integer tradeType;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 客户编号
     */
    private String customerCode;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 交易数量
     */
    private BigDecimal quantity;
    
    /**
     * 交易价格
     */
    private BigDecimal price;
    
    /**
     * 交易金额
     */
    private BigDecimal amount;
    
    /**
     * 手续费
     */
    private BigDecimal fee;
    
    /**
     * 交易状态
     */
    private Integer status;
    
    /**
     * 买方客户ID
     */
    private Long buyerCustomerId;
    
    /**
     * 卖方客户ID
     */
    private Long sellerCustomerId;
    
    /**
     * 撮合时间
     */
    private LocalDateTime matchTime;
    
    /**
     * 成交时间
     */
    private LocalDateTime executeTime;
    
    /**
     * 交易市场
     */
    private Integer market;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建交易命令处理器
     */
    @CommandHandler
    public TradingEngine(CreateTradeCommand command) {
        log.info("处理创建交易命令: {}", command);
        
        // 生成交易编号
        String tradeCode = generateTradeCode();
        
        // 计算交易金额
        BigDecimal amount = command.getQuantity().multiply(command.getPrice());
        
        // 计算手续费（简单固定费率）
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(0.0003)); // 0.03%
        
        // 应用交易创建事件
        TradeCreatedEvent event = TradeCreatedEvent.builder()
                .tradeCode(tradeCode)
                .orderCode(command.getOrderCode())
                .tradeType(command.getTradeType())
                .customerId(command.getCustomerId())
                .customerCode(command.getCustomerCode())
                .securityCode(command.getSecurityCode())
                .securityName(command.getSecurityName())
                .quantity(command.getQuantity())
                .price(command.getPrice())
                .amount(amount)
                .fee(fee)
                .market(command.getMarket())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .remark(command.getRemark())
                .build();
                
        apply(event);
    }
    
    /**
     * 撮合交易命令处理器
     */
    @CommandHandler
    public void handle(MatchTradeCommand command) {
        log.info("处理撮合交易命令: {}", command);
        
        // 检查交易状态
        if (status != 1) {
            throw new IllegalStateException("交易状态异常，无法撮合");
        }
        
        // 应用交易撮合事件
        TradeMatchedEvent event = TradeMatchedEvent.builder()
                .tradeCode(command.getTradeCode())
                .buyerCustomerId(command.getBuyerCustomerId())
                .sellerCustomerId(command.getSellerCustomerId())
                .matchPrice(command.getMatchPrice())
                .matchQuantity(command.getMatchQuantity())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .build();
                
        apply(event);
    }
    
    /**
     * 执行交易命令处理器
     */
    @CommandHandler
    public void handle(ExecuteTradeCommand command) {
        log.info("处理执行交易命令: {}", command);
        
        // 检查交易状态
        if (status != 2) {
            throw new IllegalStateException("交易状态异常，无法执行");
        }
        
        // 应用交易执行事件
        TradeExecutedEvent event = TradeExecutedEvent.builder()
                .tradeCode(command.getTradeCode())
                .executePrice(command.getExecutePrice())
                .executeQuantity(command.getExecuteQuantity())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .build();
                
        apply(event);
    }
    
    /**
     * 取消交易命令处理器
     */
    @CommandHandler
    public void handle(CancelTradeCommand command) {
        log.info("处理取消交易命令: {}", command);
        
        // 检查交易状态
        if (status == 3 || status == 4) {
            throw new IllegalStateException("交易已完成或已取消，无法再次取消");
        }
        
        // 应用交易取消事件
        TradeCancelledEvent event = TradeCancelledEvent.builder()
                .tradeCode(command.getTradeCode())
                .reason(command.getReason())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .build();
                
        apply(event);
    }
    
    /**
     * 交易创建事件处理器
     */
    @EventSourcingHandler
    public void on(TradeCreatedEvent event) {
        log.debug("应用交易创建事件: {}", event);
        
        this.tradeCode = event.getTradeCode();
        this.orderCode = event.getOrderCode();
        this.tradeType = event.getTradeType();
        this.customerId = event.getCustomerId();
        this.customerCode = event.getCustomerCode();
        this.securityCode = event.getSecurityCode();
        this.securityName = event.getSecurityName();
        this.quantity = event.getQuantity();
        this.price = event.getPrice();
        this.amount = event.getAmount();
        this.fee = event.getFee();
        this.status = 1; // 待撮合
        this.buyerCustomerId = null;
        this.sellerCustomerId = null;
        this.matchTime = null;
        this.executeTime = null;
        this.market = event.getMarket();
        this.remark = event.getRemark();
    }
    
    /**
     * 交易撮合事件处理器
     */
    @EventSourcingHandler
    public void on(TradeMatchedEvent event) {
        log.debug("应用交易撮合事件: {}", event);
        
        this.status = 2; // 部分成交
        this.buyerCustomerId = event.getBuyerCustomerId();
        this.sellerCustomerId = event.getSellerCustomerId();
        this.matchTime = event.getEventTime();
        this.price = event.getMatchPrice();
        this.quantity = event.getMatchQuantity();
    }
    
    /**
     * 交易执行事件处理器
     */
    @EventSourcingHandler
    public void on(TradeExecutedEvent event) {
        log.debug("应用交易执行事件: {}", event);
        
        this.status = 3; // 全部成交
        this.executeTime = event.getEventTime();
        this.price = event.getExecutePrice();
        this.quantity = event.getExecuteQuantity();
    }
    
    /**
     * 交易取消事件处理器
     */
    @EventSourcingHandler
    public void on(TradeCancelledEvent event) {
        log.debug("应用交易取消事件: {}", event);
        
        this.status = 4; // 已撤销
        this.remark = event.getReason();
    }
    
    /**
     * 生成交易编号
     */
    private String generateTradeCode() {
        // 简单生成规则: T + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "T" + suffix + random;
    }
}