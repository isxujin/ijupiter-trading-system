package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 客户交易流水查询对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerTransactionSummaryQuery {
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 证券代码（可选）
     */
    private String securityCode;
    
    /**
     * 交易类型（可选）
     */
    private Integer transactionType;
    
    /**
     * 交易状态（可选）
     */
    private Integer status;
    
    /**
     * 市场（可选）
     */
    private Integer market;
    
    /**
     * 开始时间（可选）
     */
    private LocalDateTime startTime;
    
    /**
     * 结束时间（可选）
     */
    private LocalDateTime endTime;
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页数量
     */
    private Integer size = 20;
}