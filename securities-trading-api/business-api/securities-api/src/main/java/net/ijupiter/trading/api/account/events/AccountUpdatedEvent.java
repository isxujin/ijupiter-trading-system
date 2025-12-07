package net.ijupiter.trading.api.account.events;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 账户更新事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class AccountUpdatedEvent {

    private final String accountId;
    private final String accountName;
    private final LocalDateTime updateTime;
    private final Long timestamp;

    public AccountUpdatedEvent(String accountId, String accountName) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.updateTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}
