package net.ijupiter.trading.api.customer.events;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 客户信息更新事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CustomerUpdatedEvent {

    private final String customerId;
    private final String customerName;
    private final String phoneNumber;
    private final String email;
    private final String address;
    private final String riskLevel;
    private final LocalDateTime updateTime;
    private final Long timestamp;

    public CustomerUpdatedEvent(String customerId, String customerName, String phoneNumber,
                               String email, String address, String riskLevel) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.riskLevel = riskLevel;
        this.updateTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}