package net.ijupiter.trading.api.product.events;

import lombok.Getter;
import net.ijupiter.trading.api.product.enums.ProductStatus;
import net.ijupiter.trading.api.product.enums.ProductType;
//import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 证券产品创建事件
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class ProductCreatedEvent {

    //@TargetAggregateIdentifier
    private final String productId;
    
    private final String productCode;
    private final String productName;
    private final ProductType productType;
    private final ProductStatus status;
    private final BigDecimal faceValue;
    private final BigDecimal issuePrice;
    private final BigDecimal minTradeUnit;
    private final BigDecimal maxTradeUnit;
    private final BigDecimal tradeUnit;
    private final BigDecimal priceTick;
    private final LocalDateTime createTime;
    private final Long timestamp;

    public ProductCreatedEvent(String productId, String productCode, String productName, 
                              ProductType productType, ProductStatus status, 
                              BigDecimal faceValue, BigDecimal issuePrice, 
                              BigDecimal minTradeUnit, BigDecimal maxTradeUnit, 
                              BigDecimal tradeUnit, BigDecimal priceTick) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.productType = productType;
        this.status = status;
        this.faceValue = faceValue;
        this.issuePrice = issuePrice;
        this.minTradeUnit = minTradeUnit;
        this.maxTradeUnit = maxTradeUnit;
        this.tradeUnit = tradeUnit;
        this.priceTick = priceTick;
        this.createTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}