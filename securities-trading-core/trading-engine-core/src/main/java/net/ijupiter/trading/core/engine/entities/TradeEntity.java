package net.ijupiter.trading.core.engine.entities;
import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import net.ijupiter.trading.common.entities.BaseEntity;

/**
 * 交易实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "trade", indexes = {
        @Index(name = "idx_buy_order_id", columnList = "buy_order_id"),
        @Index(name = "idx_sell_order_id", columnList = "sell_order_id"),
        @Index(name = "idx_product_id", columnList = "product_id"),
        @Index(name = "idx_trade_time", columnList = "trade_time")
})
public class TradeEntity extends BaseEntity {

    @Id
    @Column(name = "trade_id")
    private String tradeId;

    @Column(name = "buy_order_id", nullable = false)
    private String buyOrderId;

    @Column(name = "sell_order_id", nullable = false)
    private String sellOrderId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "price", nullable = false, precision = 18, scale = 4)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false, precision = 18, scale = 4)
    private BigDecimal quantity;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "trade_time", nullable = false)
    private LocalDateTime tradeTime;
}