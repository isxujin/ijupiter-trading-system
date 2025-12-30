package net.ijupiter.trading.api.query.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 客户资金流水查询DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CustomerFundingTransactionSummaryDTO extends BaseDTO<CustomerFundingTransactionSummaryDTO> {
    
    /**
     * 流水ID
     */
    private Long transactionId;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 资金账户ID
     */
    private Long accountId;
    
    /**
     * 账户编号
     */
    private String accountNumber;
    
    /**
     * 账户名称
     */
    private String accountName;
    
    /**
     * 交易类型 (1:存入, 2:取出, 3:转账转入, 4:转账转出, 5:交易买入, 6:交易卖出, 7:费用扣除)
     */
    private Integer transactionType;
    
    /**
     * 交易金额
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
     * 可用余额变化
     */
    private BigDecimal availableBalanceChange;
    
    /**
     * 冻结金额变化
     */
    private BigDecimal frozenAmountChange;
    
    /**
     * 交易状态 (1:成功, 2:失败)
     */
    private Integer status;
    
    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;
    
    /**
     * 关联交易编号
     */
    private String relatedTransactionCode;
    
    /**
     * 交易描述
     */
    private String description;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 操作员姓名
     */
    private String operatorName;
}