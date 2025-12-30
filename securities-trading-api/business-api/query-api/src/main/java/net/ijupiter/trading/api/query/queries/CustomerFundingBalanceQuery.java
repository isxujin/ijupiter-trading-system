package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 客户资金账户余额查询对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerFundingBalanceQuery {
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 账户ID（可选）
     */
    private Long accountId;
    
    /**
     * 账户类型（可选）
     */
    private String accountType;
    
    /**
     * 账户状态（可选）
     */
    private Integer status;
    
    /**
     * 开始时间（可选，用于计算发生额）
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间（可选，用于计算发生额）
     */
    private LocalDateTime endTime;
    
    /**
     * 是否包含日发生额
     */
    private Boolean includeDailyChanges = true;
    
    /**
     * 是否包含月发生额
     */
    private Boolean includeMonthlyChanges = true;
    
    /**
     * 是否包含年发生额
     */
    private Boolean includeYearlyChanges = true;
}