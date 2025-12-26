package net.ijupiter.trading.core.funding.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金流水实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FundingTransaction extends BaseEntity<FundingTransaction> {
    /**
     * 交易编号
     */
    private String transactionCode;
    
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
     * 交易类型(1:存款,2:取款,3:转入,4:转出,5:冻结,6:解冻,7:利息)
     */
    private Integer transactionType;
    
    /**
     * 交易金额(正数表示增加，负数表示减少)
     */
    private BigDecimal amount;
    
    /**
     * 交易前余额
     */
    private BigDecimal balanceBefore;
    
    /**
     * 交易后余额
     */
    private BigDecimal balanceAfter;
    
    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;
    
    /**
     * 关联转账编号(如果是转账相关的交易)
     */
    private String relatedTransferCode;
    
    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 备注
     */
    private String remark;
}