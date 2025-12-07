package net.ijupiter.trading.api.product.commands;

import lombok.Getter;
import net.ijupiter.trading.api.product.enums.ProductType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 创建证券产品命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CreateProductCommand {

    @NotBlank
    private final String productId;
    
    @NotBlank
    private final String productCode;
    
    @NotBlank
    private final String productName;
    
    @NotNull
    private final ProductType productType;
    
    @Positive
    private final BigDecimal faceValue;
    
    @Positive
    private final BigDecimal issuePrice;
    
    @Positive
    private final BigDecimal minTradeUnit;
    
    @Positive
    private final BigDecimal maxTradeUnit;
    
    @Positive
    private final BigDecimal tradeUnit;
    
    @Positive
    private final BigDecimal priceTick;

    public CreateProductCommand(String productId, String productCode, String productName, 
                               ProductType productType, BigDecimal faceValue, 
                               BigDecimal issuePrice, BigDecimal minTradeUnit, 
                               BigDecimal maxTradeUnit, BigDecimal tradeUnit, 
                               BigDecimal priceTick) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.productType = productType;
        this.faceValue = faceValue;
        this.issuePrice = issuePrice;
        this.minTradeUnit = minTradeUnit;
        this.maxTradeUnit = maxTradeUnit;
        this.tradeUnit = tradeUnit;
        this.priceTick = priceTick;
    }
}