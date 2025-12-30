package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 客户综合信息查询对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerFinancialSummaryQuery {
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 开始时间（可选）
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间（可选）
     */
    private LocalDateTime endTime;
    
    /**
     * 是否包含资金账户信息
     */
    private Boolean includeFundingAccounts = true;
    
    /**
     * 是否包含证券账户信息
     */
    private Boolean includeSecuritiesAccounts = true;
    
    /**
     * 是否包含账户余额
     */
    private Boolean includeAccountBalance = true;
}