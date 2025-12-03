package net.ijupiter.trading.core.account.entities;

import lombok.Data;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.account.enums.AccountType;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 账户实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "account", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_account_type", columnList = "account_type"),
        @Index(name = "idx_account_status", columnList = "account_status"),
        @Index(name = "idx_user_type_status", columnList = "user_id, account_type, account_status")
})
public class AccountEntity {

    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "close_time")
    private LocalDateTime closeTime;

    @Column(name = "close_reason", length = 200)
    private String closeReason;
}