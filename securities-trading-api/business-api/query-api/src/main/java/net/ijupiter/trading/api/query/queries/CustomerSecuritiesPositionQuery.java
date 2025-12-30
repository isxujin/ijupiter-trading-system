package net.ijupiter.trading.api.query.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * 客户证券持仓信息查询对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerSecuritiesPositionQuery {
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 证券代码（可选）
     */
    private String securityCode;
    
    /**
     * 证券账户ID（可选）
     */
    private Long securitiesAccountId;
    
    /**
     * 证券类型（可选）
     */
    private Integer securityType;
    
    /**
     * 是否包含盈利计算
     */
    private Boolean includeProfitCalculation = true;
    
    /**
     * 是否包含盈利率
     */
    private Boolean includeProfitRate = true;
    
    /**
     * 页码
     */
    private Integer page = 1;
    
    /**
     * 每页数量
     */
    private Integer size = 20;
}