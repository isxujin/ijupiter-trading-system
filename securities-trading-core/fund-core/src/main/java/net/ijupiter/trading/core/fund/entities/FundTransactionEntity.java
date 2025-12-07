package net.ijupiter.trading.core.fund.entities;

import lombok.Data;
import net.ijupiter.trading.api.fund.enums.DepositType;
import net.ijupiter.trading.api.fund.enums.FundTransactionType;
import net.ijupiter.trading.api.fund.enums.WithdrawType;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金交易实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "fund_transaction", indexes = {
        @Index(name = "idx_fund_account_id", columnList = "fund_account_id"),
        @Index(name = "idx_account_id", columnList = "account_id"),
        @Index(name = "idx_transaction_id", columnList = "transaction_id"),
        @Index(name = "idx_transaction_type", columnList = "transaction_type"),
        @Index(name = "idx_transaction_time", columnList = "transaction_time")
})
public class FundTransactionEntity {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "fund_account_id", nullable = false)
    private String fundAccountId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private FundTransactionType transactionType;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "balance", nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;

    @Column(name = "frozen_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal frozenAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "deposit_type")
    private DepositType depositType;

    @Enumerated(EnumType.STRING)
    @Column(name = "withdraw_type")
    private WithdrawType withdrawType;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime transactionTime;
}