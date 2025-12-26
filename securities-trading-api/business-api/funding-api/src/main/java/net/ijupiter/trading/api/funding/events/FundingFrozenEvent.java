package net.ijupiter.trading.api.funding.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金冻结事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundingFrozenEvent {
    /**
     * 账户编号
     */
    private String accountCode;
    
    /**
     * 冻结金额
     */
    private BigDecimal amount;
    
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