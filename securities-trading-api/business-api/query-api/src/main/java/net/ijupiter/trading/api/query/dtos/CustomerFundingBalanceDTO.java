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
 * 客户资金账户余额和发生额查询DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CustomerFundingBalanceDTO extends BaseDTO<CustomerFundingBalanceDTO> {
    
    /**
     * 账户ID
     */
    private Long accountId;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 账户编号
     */
    private String accountNumber;
    
    /**
     * 账户名称
     */
    private String accountName;
    
    /**
     * 账户类型
     */
    private String accountType;
    
    /**
     * 账户状态
     */
    private Integer status;
    
    /**
     * 当前余额
     */
    private BigDecimal currentBalance;
    
    /**
     * 可用余额
     */
    private BigDecimal availableBalance;
    
    /**
     * 冻结余额
     */
    private BigDecimal frozenBalance;
    
    /**
     * 日初余额
     */
    private BigDecimal dailyBeginBalance;
    
    /**
     * 本日存入金额
     */
    private BigDecimal dailyDepositAmount;
    
    /**
     * 本日取出金额
     */
    private BigDecimal dailyWithdrawAmount;
    
    /**
     * 本日净变化金额
     */
    private BigDecimal dailyNetChangeAmount;
    
    /**
     * 月初余额
     */
    private BigDecimal monthlyBeginBalance;
    
    /**
     * 本月存入金额
     */
    private BigDecimal monthlyDepositAmount;
    
    /**
     * 本月取出金额
     */
    private BigDecimal monthlyWithdrawAmount;
    
    /**
     * 本月净变化金额
     */
    private BigDecimal monthlyNetChangeAmount;
    
    /**
     * 年初余额
     */
    private BigDecimal yearlyBeginBalance;
    
    /**
     * 本年存入金额
     */
    private BigDecimal yearlyDepositAmount;
    
    /**
     * 本年取出金额
     */
    private BigDecimal yearlyWithdrawAmount;
    
    /**
     * 本年净变化金额
     */
    private BigDecimal yearlyNetChangeAmount;
    
    /**
     * 账户创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 账户更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 最后操作时间
     */
    private LocalDateTime lastOperationTime;
}