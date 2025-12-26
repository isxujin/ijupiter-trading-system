package net.ijupiter.trading.api.funding.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金转账事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundingTransferredEvent {
    /**
     * 转账编号
     */
    private String transferCode;
    
    /**
     * 转出账户编号
     */
    private String fromAccountCode;
    
    /**
     * 转入账户编号
     */
    private String toAccountCode;
    
    /**
     * 转账金额
     */
    private BigDecimal amount;
    
    /**
     * 转账类型(1:内部转账,2:外部转账)
     */
    private Integer transferType;
    
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