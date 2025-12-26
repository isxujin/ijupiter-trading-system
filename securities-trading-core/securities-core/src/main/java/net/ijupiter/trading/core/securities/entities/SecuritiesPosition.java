package net.ijupiter.trading.core.securities.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 证券持仓实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SecuritiesPosition extends BaseEntity<SecuritiesPosition> {
    /**
     * 账户编号
     */
    private String accountCode;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 客户编号
     */
    private String customerCode;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 证券类型(1:股票,2:基金,3:债券,4:衍生品)
     */
    private Integer securityType;
    
    /**
     * 持仓数量
     */
    private BigDecimal quantity;
    
    /**
     * 平均成本价
     */
    private BigDecimal avgCostPrice;
    
    /**
     * 当前价格
     */
    private BigDecimal currentPrice;
    
    /**
     * 持仓市值
     */
    private BigDecimal marketValue;
    
    /**
     * 冻结数量
     */
    private BigDecimal frozenQuantity;
    
    /**
     * 可用数量
     */
    private BigDecimal availableQuantity;
    
    /**
     * 浮动盈亏(市值-成本)
     */
    private BigDecimal unrealizedPnL;
    
    /**
     * 盈亏率(浮动盈亏/成本)
     */
    private BigDecimal pnlRate;
    
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}