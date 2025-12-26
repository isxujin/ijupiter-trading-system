package net.ijupiter.trading.api.trading.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易执行事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class TradeExecutedEvent {
    /**
     * 交易编号
     */
    private String tradeCode;
    
    /**
     * 成交价格
     */
    private BigDecimal executePrice;
    
    /**
     * 成交数量
     */
    private BigDecimal executeQuantity;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
}