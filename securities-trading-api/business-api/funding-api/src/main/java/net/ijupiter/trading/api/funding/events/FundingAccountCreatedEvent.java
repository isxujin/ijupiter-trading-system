package net.ijupiter.trading.api.funding.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户创建事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundingAccountCreatedEvent {
    /**
     * 账户编号
     */
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
     * 初始余额
     */
    private BigDecimal initialBalance;
    
    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
    
    /**
     * 备注
     */
    private String remark;
}