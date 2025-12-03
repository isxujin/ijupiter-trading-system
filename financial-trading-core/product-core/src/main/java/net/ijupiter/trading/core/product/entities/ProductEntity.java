package net.ijupiter.trading.core.product.entities;

import lombok.Data;
import net.ijupiter.trading.api.product.enums.ProductStatus;
import net.ijupiter.trading.api.product.enums.ProductType;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 证券产品实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_product_code", columnList = "product_code"),
        @Index(name = "idx_product_type", columnList = "product_type"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_type_status", columnList = "product_type, status")
})
public class ProductEntity {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_code", nullable = false, unique = true, length = 20)
    private String productCode;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @Column(name = "face_value", precision = 18, scale = 2)
    private BigDecimal faceValue;

    @Column(name = "issue_price", precision = 18, scale = 4)
    private BigDecimal issuePrice;

    @Column(name = "current_price", precision = 18, scale = 4)
    private BigDecimal currentPrice;

    @Column(name = "min_trade_unit", precision = 18, scale = 4)
    private BigDecimal minTradeUnit;

    @Column(name = "max_trade_unit", precision = 18, scale = 4)
    private BigDecimal maxTradeUnit;

    @Column(name = "trade_unit", precision = 18, scale = 4)
    private BigDecimal tradeUnit;

    @Column(name = "price_tick", precision = 18, scale = 4)
    private BigDecimal priceTick;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "suspend_time")
    private LocalDateTime suspendTime;

    @Column(name = "resume_time")
    private LocalDateTime resumeTime;

    @Column(name = "delist_time")
    private LocalDateTime delistTime;

    @Column(name = "suspend_reason", length = 200)
    private String suspendReason;

    @Column(name = "resume_reason", length = 200)
    private String resumeReason;

    @Column(name = "delist_reason", length = 200)
    private String delistReason;
}