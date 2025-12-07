package net.ijupiter.trading.core.fund.entities;

import lombok.Data;
import net.ijupiter.trading.api.fund.enums.FundAccountStatus;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "fund_account", indexes = {
        @Index(name = "idx_account_id", columnList = "account_id"),
        @Index(name = "idx_status", columnList = "status")
})
public class FundAccountEntity {

    @Id
    @Column(name = "fund_account_id")
    private String fundAccountId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;

    @Column(name = "frozen_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal frozenAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FundAccountStatus status;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}