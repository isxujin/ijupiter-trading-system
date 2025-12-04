package net.ijupiter.trading.core.engine.entities;

import lombok.Data;
import net.ijupiter.trading.api.engine.enums.OrderSide;
import net.ijupiter.trading.api.engine.enums.OrderStatus;
import net.ijupiter.trading.api.engine.enums.OrderType;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "trading_order", indexes = {
        @Index(name = "idx_account_id", columnList = "account_id"),
        @Index(name = "idx_product_id", columnList = "product_id"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_side", columnList = "side"),
        @Index(name = "idx_product_status_side", columnList = "product_id, status, side")
})
public class OrderEntity {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "side", nullable = false)
    private OrderSide side;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private OrderType type;

    @Column(name = "price", nullable = false, precision = 18, scale = 4)
    private BigDecimal price;

    @Column(name = "original_quantity", nullable = false, precision = 18, scale = 4)
    private BigDecimal originalQuantity;

    @Column(name = "filled_quantity", nullable = false, precision = 18, scale = 4)
    private BigDecimal filledQuantity;

    @Column(name = "remaining_quantity", nullable = false, precision = 18, scale = 4)
    private BigDecimal remainingQuantity;

    @Column(name = "average_price", precision = 18, scale = 4)
    private BigDecimal averagePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;

    @Column(name = "cancel_reason", length = 200)
    private String cancelReason;
}