package net.ijupiter.trading.core.customer.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户余额实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fund_account_balance", indexes = {
        @Index(name = "idx_account_code", columnList = "account_code"),
        @Index(name = "idx_customer_id", columnList = "customer_id"),
        @Index(name = "idx_account_id", columnList = "account_id"),
        @Index(name = "idx_last_update_time", columnList = "last_update_time")
})
public class FundAccountBalanceEntity extends BaseEntity<FundAccountBalanceEntity> {
    
    /**
     * 资金账户余额ID
     */
    @Id
    @Column(name = "balance_id")
    private String balanceId;

    /**
     * 资金账户ID
     */
    @Column(name = "account_id", nullable = false)
    private String accountId;

    /**
     * 资金账户编号
     */
    @Column(name = "account_code", nullable = false, length = 50)
    private String accountCode;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @Column(name = "available_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal availableBalance;

    @Column(name = "frozen_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal frozenAmount;

    @Column(name = "total_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalBalance;

    @Column(name = "daily_deposit_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal dailyDepositAmount;

    @Column(name = "daily_withdraw_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal dailyWithdrawAmount;

    @Column(name = "daily_net_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal dailyNetAmount;

    @Column(name = "last_update_time", nullable = false)
    private LocalDateTime lastUpdateTime;
}