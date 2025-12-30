package net.ijupiter.trading.core.query.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 证券持仓查询实体类
 * 专门用于查询模块，不依赖业务模块实体
 */
@Entity
@Table(name = "secu_securities_position")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecuritiesPositionQueryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "securities_account_id")
    private Long securitiesAccountId;
    
    @Column(name = "security_code")
    private String securityCode;
    
    @Column(name = "security_name")
    private String securityName;
    
    @Column(name = "position_volume")
    private Long positionVolume;
    
    @Column(name = "available_volume")
    private Long availableVolume;
    
    @Column(name = "frozen_volume")
    private Long frozenVolume;
    
    @Column(name = "average_price")
    private BigDecimal averagePrice;
    
    @Column(name = "market_price")
    private BigDecimal marketPrice;
    
    @Column(name = "market_value")
    private BigDecimal marketValue;
    
    @Column(name = "cost_value")
    private BigDecimal costValue;
    
    @Column(name = "profit_loss")
    private BigDecimal profitLoss;
    
    @Column(name = "profit_loss_ratio")
    private BigDecimal profitLossRatio;
    
    @Column(name = "trade_date")
    private LocalDate tradeDate;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "updated_by")
    private String updatedBy;
}