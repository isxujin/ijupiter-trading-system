package net.ijupiter.trading.api.customer.events;

import lombok.Getter;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;

import java.time.LocalDateTime;

/**
 * 客户创建事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CustomerCreatedEvent {

    private final String customerId;
    private final String customerCode;
    private final String customerName;
    private final String idCardNumber;
    private final String phoneNumber;
    private final String email;
    private final String address;
    private final String riskLevel;
    private final CustomerStatus status;
    private final LocalDateTime createTime;
    private final Long timestamp;

    public CustomerCreatedEvent(String customerId, String customerCode, String customerName,
                               String idCardNumber, String phoneNumber, String email,
                               String address, String riskLevel, CustomerStatus status) {
        this.customerId = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.idCardNumber = idCardNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.riskLevel = riskLevel;
        this.status = status;
        this.createTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}