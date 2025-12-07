package net.ijupiter.trading.api.account.events;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 账户关闭事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class AccountClosedEvent {

    private final String accountId;
    private final String reason;
    private final LocalDateTime closeTime;
    private final Long timestamp;

    public AccountClosedEvent(String accountId, String reason) {
        this.accountId = accountId;
        this.reason = reason;
        this.closeTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}