package net.ijupiter.trading.core.customer.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;

import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.time.LocalDateTime;

/**
 * 客户实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customer", indexes = {
        @Index(name = "idx_customer_code", columnList = "customer_code"),
        @Index(name = "idx_customer_status", columnList = "customer_status"),
        @Index(name = "idx_phone_number", columnList = "phone_number"),
        @Index(name = "idx_id_card_number", columnList = "id_card_number"),
        @Index(name = "idx_customer_status_risk", columnList = "customer_status, risk_level")
})
public class CustomerEntity extends BaseEntity<CustomerEntity> {
    
    /**
     * 客户ID
     */
    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_code", nullable = false, unique = true, length = 50)
    private String customerCode;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "id_card_number", nullable = false, unique = true, length = 20)
    private String idCardNumber;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "risk_level", nullable = false, length = 20)
    private String riskLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_status", nullable = false)
    private CustomerStatus customerStatus;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "create_time")
    private LocalDateTime openDate;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "cancel_time")
    private LocalDateTime closeDate;

    @Column(name = "cancel_reason", length = 200)
    private String closeReason;

    @Column(name = "freeze_time")
    private LocalDateTime freezeDate;

    @Column(name = "freeze_reason", length = 200)
    private String freezeReason;
}