package net.ijupiter.trading.api.trading.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易撮合事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class TradeMatchedEvent {
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
     * 撮合价格
     */
    private BigDecimal matchPrice;
    
    /**
     * 撮合数量
     */
    private BigDecimal matchQuantity;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
}