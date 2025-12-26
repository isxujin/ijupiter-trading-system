package net.ijupiter.trading.api.trading.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 交易取消事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class TradeCancelledEvent {
    /**
     * 交易编号
     */
    private String tradeCode;
    
    /**
     * 取消原因
     */
    private String reason;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
}