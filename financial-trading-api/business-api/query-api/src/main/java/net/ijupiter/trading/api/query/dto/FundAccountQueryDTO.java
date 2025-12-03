package net.ijupiter.trading.api.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户查询DTO
 * 
 * @author ijupiter
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundAccountQueryDTO {
    
    /**
     * 资金账户ID
     */
    private String fundAccountId;
    
    /**
     * 客户ID
     */
    private String customerId;
    
    /**
     * 账户ID
     */
    private String accountId;
    
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
     * 总资产
     */
    private BigDecimal totalAssets;
    
    /**
     * 可用资金
     */
    private BigDecimal availableBalance;
    
    /**
     * 冻结资金
     */
    private BigDecimal frozenBalance;
    
    /**
     * 在途资金
     */
    private BigDecimal transitBalance;
    
    /**
     * 币种
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