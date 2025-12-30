package net.ijupiter.trading.api.query.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户综合信息查询DTO
 * 包含客户基础信息、资金账户信息和证券账户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CustomerFinancialSummaryDTO extends BaseDTO<CustomerFinancialSummaryDTO> {
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 客户名称
     */
    private String customerName;
    
    /**
     * 客户类型
     */
    private Integer customerType;
    
    /**
     * 客户状态
     */
    private Integer customerStatus;
    
    /**
     * 注册时间
     */
    private LocalDateTime registrationTime;
    
    /**
     * 资金账户信息列表
     */
    private List<FundingAccountSummaryDTO> fundingAccounts;
    
    /**
     * 证券账户信息列表
     */
    private List<SecuritiesAccountSummaryDTO> securitiesAccounts;
    
    /**
     * 资金总余额
     */
    private BigDecimal totalFundingBalance;
    
    /**
     * 证券持仓总市值
     */
    private BigDecimal totalSecuritiesValue;
    
    /**
     * 客户总资产
     */
    private BigDecimal totalAssets;
    
    /**
     * 资金账户汇总DTO
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Accessors(chain = true)
    public static class FundingAccountSummaryDTO extends BaseDTO<FundingAccountSummaryDTO> {
        private Long accountId;
        private String accountNumber;
        private String accountName;
        private BigDecimal balance;
        private BigDecimal availableBalance;
        private BigDecimal frozenAmount;
        private String accountType;
        private Integer status;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
    }
    
    /**
     * 证券账户汇总DTO
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Accessors(chain = true)
    public static class SecuritiesAccountSummaryDTO extends BaseDTO<SecuritiesAccountSummaryDTO> {
        private Long accountId;
        private String accountNumber;
        private String accountName;
        private String securitiesCode;
        private String securitiesName;
        private BigDecimal totalAmount;
        private BigDecimal availableAmount;
        private BigDecimal frozenAmount;
        private Integer status;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
    }
}