package net.ijupiter.trading.core.customer.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.enums.FundAccountType;

import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.time.LocalDateTime;

/**
 * 资金账户基本信息实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fund_account_basic_info", indexes = {
        @Index(name = "idx_account_code", columnList = "account_code"),
        @Index(name = "idx_customer_id", columnList = "customer_id"),
        @Index(name = "idx_account_type", columnList = "account_type"),
        @Index(name = "idx_account_status", columnList = "account_status"),
        @Index(name = "idx_customer_status", columnList = "customer_id, account_status"),
        @Index(name = "idx_bank_card_number", columnList = "bank_card_number")
})
public class FundAccountEntity extends BaseEntity<FundAccountEntity> {
    
    /**
     * 资金账户基本信息ID
     */
    @Id
    @Column(name = "basic_info_id")
    private String basicInfoId;

    /**
     * 资金账户ID
     */
    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "account_code", nullable = false, unique = true, length = 50)
    private String accountCode;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private FundAccountType accountType;

    @Column(name = "bank_card_number", nullable = false, length = 20)
    private String bankCardNumber;

    @Column(name = "bank_code", nullable = false, length = 10)
    private String bankCode;

    @Column(name = "bank_name", nullable = false, length = 100)
    private String bankName;

    @Column(name = "holder_name", nullable = false, length = 100)
    private String holderName;

    @Column(name = "currency", nullable = false, length = 10)
    private String currency;

    @Column(name = "create_time")
    private LocalDateTime openDate;

    @Column(name = "close_time")
    private LocalDateTime closeDate;

    @Column(name = "close_reason", length = 200)
    private String closeReason;
}