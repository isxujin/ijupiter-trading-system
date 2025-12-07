package net.ijupiter.trading.api.customer.events;

import lombok.Getter;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;

import java.time.LocalDateTime;

/**
 * 客户冻结事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CustomerFrozenEvent {

    private final String customerId;
    private final CustomerStatus status;
    private final String reason;
    private final LocalDateTime freezeTime;
    private final Long timestamp;

    public CustomerFrozenEvent(String customerId, CustomerStatus status, String reason) {
        this.customerId = customerId;
        this.status = status;
        this.reason = reason;
        this.freezeTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}