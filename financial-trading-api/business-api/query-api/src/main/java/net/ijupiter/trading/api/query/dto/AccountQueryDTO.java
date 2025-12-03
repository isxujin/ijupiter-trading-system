package net.ijupiter.trading.api.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户查询DTO
 * 
 * @author ijupiter
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountQueryDTO {
    
    /**
     * 账户ID
     */
    private String accountId;
    
    /**
     * 客户ID
     */
    private String customerId;
    
    /**
     * 账户编号
     */
    private String accountNo;
    
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
    private String status;
    
    /**
     * 账户余额
     */
    private BigDecimal balance;
    
    /**
     * 可用余额
     */
    private BigDecimal availableBalance;
    
    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;
    
    /**
     * 账币
     */
    private String currency;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}