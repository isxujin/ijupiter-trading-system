package net.ijupiter.trading.core.customer.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易账户持仓实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "trading_account_position", indexes = {
        @Index(name = "idx_account_code", columnList = "account_code"),
        @Index(name = "idx_customer_id", columnList = "customer_id"),
        @Index(name = "idx_account_id", columnList = "account_id"),
        @Index(name = "idx_last_update_time", columnList = "last_update_time")
})
public class TradingAccountPositionEntity extends BaseEntity<TradingAccountPositionEntity> {
    
    /**
     * 交易账户持仓ID
     */
    @Id
    @Column(name = "position_id")
    private String positionId;

    /**
     * 交易账户ID
     */
    @Column(name = "account_id", nullable = false)
    private String accountId;

    /**
     * 交易账户编号
     */
    @Column(name = "account_code", nullable = false, length = 50)
    private String accountCode;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "position_shares", nullable = false, precision = 19, scale = 4)
    private BigDecimal positionShares;

    @Column(name = "available_shares", nullable = false, precision = 19, scale = 4)
    private BigDecimal availableShares;

    @Column(name = "frozen_shares", nullable = false, precision = 19, scale = 4)
    private BigDecimal frozenShares;

    @Column(name = "daily_buy_shares", nullable = false, precision = 19, scale = 4)
    private BigDecimal dailyBuyShares;

    @Column(name = "daily_sell_shares", nullable = false, precision = 19, scale = 4)
    private BigDecimal dailySellShares;

    @Column(name = "daily_net_shares", nullable = false, precision = 19, scale = 4)
    private BigDecimal dailyNetShares;

    @Column(name = "total_market_value", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalMarketValue;

    @Column(name = "available_market_value", nullable = false, precision = 19, scale = 4)
    private BigDecimal availableMarketValue;

    @Column(name = "frozen_market_value", nullable = false, precision = 19, scale = 4)
    private BigDecimal frozenMarketValue;

    @Column(name = "last_update_time", nullable = false)
    private LocalDateTime lastUpdateTime;
}