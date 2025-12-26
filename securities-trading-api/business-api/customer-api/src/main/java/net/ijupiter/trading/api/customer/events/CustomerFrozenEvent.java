package net.ijupiter.trading.api.customer.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 客户冻结事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerFrozenEvent {
    /**
     * 客户编号
     */
    private String customerCode;
    
    /**
     * 冻结原因
     */
    private String reason;
    
    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
}