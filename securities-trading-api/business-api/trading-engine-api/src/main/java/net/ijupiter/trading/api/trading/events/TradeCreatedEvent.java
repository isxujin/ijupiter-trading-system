package net.ijupiter.trading.api.trading.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易创建事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class TradeCreatedEvent {
    /**
     * 交易编号
     */
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
     * 交易市场
     */
    private Integer market;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
    
    /**
     * 备注
     */
    private String remark;
}