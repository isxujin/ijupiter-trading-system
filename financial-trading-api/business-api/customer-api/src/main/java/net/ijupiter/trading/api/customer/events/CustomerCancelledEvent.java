package net.ijupiter.trading.api.customer.events;

import lombok.Getter;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;

import java.time.LocalDateTime;

/**
 * 客户注销事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CustomerCancelledEvent {

    private final String customerId;
    private final CustomerStatus status;
    private final String reason;
    private final LocalDateTime cancelTime;
    private final Long timestamp;

    public CustomerCancelledEvent(String customerId, CustomerStatus status, String reason) {
        this.customerId = customerId;
        this.status = status;
        this.reason = reason;
        this.cancelTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}