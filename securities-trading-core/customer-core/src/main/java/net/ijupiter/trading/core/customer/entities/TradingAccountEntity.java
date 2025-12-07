package net.ijupiter.trading.core.customer.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.enums.TradingAccountType;

import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.time.LocalDateTime;

/**
 * 交易账户基本信息实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "trading_account_basic_info", indexes = {
        @Index(name = "idx_account_code", columnList = "account_code"),
        @Index(name = "idx_customer_id", columnList = "customer_id"),
        @Index(name = "idx_exchange_code", columnList = "exchange_code"),
        @Index(name = "idx_account_type", columnList = "account_type"),
        @Index(name = "idx_account_status", columnList = "account_status"),
        @Index(name = "idx_customer_status", columnList = "customer_id, account_status")
})
public class TradingAccountEntity extends BaseEntity<TradingAccountEntity> {
    
    /**
     * 交易账户基本信息ID
     */
    @Id
    @Column(name = "basic_info_id")
    private String basicInfoId;

    /**
     * 交易账户ID
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
    private TradingAccountType accountType;

    @Column(name = "exchange_code", nullable = false, length = 20)
    private String exchangeCode;

    @Column(name = "exchange_name", nullable = false, length = 100)
    private String exchangeName;

    @Column(name = "exchange_account_number", nullable = false, length = 50)
    private String exchangeAccountNumber;

    /**
     * 加密存储的交易密码
     */
    @Column(name = "trading_password", length = 500)
    private String tradingPassword;

    /**
     * 加密存储的资金密码
     */
    @Column(name = "fund_password", length = 500)
    private String fundPassword;

    /**
     * 加密存储的API密钥
     */
    @Column(name = "api_key", length = 500)
    private String apiKey;

    /**
     * 加密存储的API密钥密钥
     */
    @Column(name = "api_secret", length = 500)
    private String apiSecret;

    @Column(name = "trading_product", nullable = false, length = 100)
    private String tradingProduct;

    @Column(name = "create_time")
    private LocalDateTime openDate;

    @Column(name = "close_time")
    private LocalDateTime closeDate;

    @Column(name = "close_reason", length = 200)
    private String closeReason;
}