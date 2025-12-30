package net.ijupiter.trading.core.query.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 每日证券收益查询实体类
 * 专门用于查询模块，不依赖业务模块实体
 */
@Entity
@Table(name = "daily_securities_profit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailySecuritiesProfitQueryEntity {
    
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
    
    @Column(name = "trade_date")
    private LocalDate tradeDate;
    
    @Column(name = "open_price")
    private BigDecimal openPrice;
    
    @Column(name = "close_price")
    private BigDecimal closePrice;
    
    @Column(name = "position_volume")
    private Long positionVolume;
    
    @Column(name = "market_value_start")
    private BigDecimal marketValueStart;
    
    @Column(name = "market_value_end")
    private BigDecimal marketValueEnd;
    
    @Column(name = "daily_profit_loss")
    private BigDecimal dailyProfitLoss;
    
    @Column(name = "daily_profit_loss_ratio")
    private BigDecimal dailyProfitLossRatio;
    
    @Column(name = "cumulative_profit_loss")
    private BigDecimal cumulativeProfitLoss;
    
    @Column(name = "cumulative_profit_loss_ratio")
    private BigDecimal cumulativeProfitLossRatio;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "updated_by")
    private String updatedBy;
}