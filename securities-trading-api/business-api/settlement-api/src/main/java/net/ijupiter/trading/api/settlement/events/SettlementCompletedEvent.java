package net.ijupiter.trading.api.settlement.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 清算完成事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class SettlementCompletedEvent {
    /**
     * 清算编号
     */
    private String settlementCode;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
}