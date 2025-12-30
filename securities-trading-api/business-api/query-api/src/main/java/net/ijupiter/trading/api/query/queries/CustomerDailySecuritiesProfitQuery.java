package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

/**
 * 客户每日证券收益信息查询对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDailySecuritiesProfitQuery {
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 证券代码（可选）
     */
    private String securityCode;
    
    /**
     * 开始日期（可选）
     */
    private LocalDate startDate;
    
    /**
     * 结束日期（可选）
     */
    private LocalDate endDate;
    
    /**
     * 是否包含盈亏计算
     */
    private Boolean includeProfitLossCalculation = true;
    
    /**
     * 是否包含涨跌幅计算
     */
    private Boolean includeChangeRateCalculation = true;
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页数量
     */
    private Integer size = 20;
}