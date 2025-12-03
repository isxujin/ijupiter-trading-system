package net.ijupiter.trading.api.account.events;

import lombok.Getter;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.account.enums.AccountType;

import java.time.LocalDateTime;

/**
 * 账户创建事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class AccountCreatedEvent {

    private final String accountId;
    private final String userId;
    private final String accountName;
    private final AccountType accountType;
    private final AccountStatus status;
    private final LocalDateTime createTime;
    private final Long timestamp;

    public AccountCreatedEvent(String accountId, String userId, String accountName, 
                               AccountType accountType, AccountStatus status) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.status = status;
        this.createTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}