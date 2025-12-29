package net.ijupiter.trading.core.securities.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "secu_securities_position")
public class SecuritiesPositionEntity extends BaseEntity<SecuritiesPositionEntity> {
    
    /**
     * 账户编号
     */
    @Column(name = "account_code", nullable = false, length = 50)
    private String accountCode;
    
    /**
     * 客户ID
     */
    @Column(name = "customer_id")
    private Long customerId;
    
    /**
     * 证券代码
     */
    @Column(name = "security_code", nullable = false, length = 20)
    private String securityCode;
    
    /**
     * 证券名称
     */
    @Column(name = "security_name", length = 100)
    private String securityName;
    
    /**
     * 持仓数量
     */
    @Column(name = "quantity", precision = 20, scale = 8)
    private BigDecimal quantity;
    
    /**
     * 可用数量
     */
    @Column(name = "available_quantity", precision = 20, scale = 8)
    private BigDecimal availableQuantity;
    
    /**
     * 冻结数量
     */
    @Column(name = "frozen_quantity", precision = 20, scale = 8)
    private BigDecimal frozenQuantity;
    
    /**
     * 成本价
     */
    @Column(name = "cost_price", precision = 20, scale = 8)
    private BigDecimal costPrice;
    
    /**
     * 市值
     */
    @Column(name = "market_value", precision = 20, scale = 2)
    private BigDecimal marketValue;
    
    /**
     * 盈亏金额
     */
    @Column(name = "profit_loss", precision = 20, scale = 2)
    private BigDecimal profitLoss;
    
    /**
     * 盈亏率
     */
    @Column(name = "profit_loss_rate", precision = 10, scale = 6)
    private BigDecimal profitLossRate;
    
    /**
     * 最后更新时间
     */
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
    
    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;

}