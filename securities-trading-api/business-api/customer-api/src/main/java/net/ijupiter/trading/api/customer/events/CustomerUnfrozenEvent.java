package net.ijupiter.trading.api.customer.events;

import lombok.Getter;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;

import java.time.LocalDateTime;

/**
 * 客户解冻事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CustomerUnfrozenEvent {

    private final String customerId;
    private final CustomerStatus status;
    private final String reason;
    private final LocalDateTime unfreezeTime;
    private final Long timestamp;

    public CustomerUnfrozenEvent(String customerId, CustomerStatus status, String reason) {
        this.customerId = customerId;
        this.status = status;
        this.reason = reason;
        this.unfreezeTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}