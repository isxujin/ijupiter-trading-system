package net.ijupiter.trading.api.product.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 更新证券产品命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class UpdateProductCommand {

    @NotBlank
    private final String productId;
    
    @NotBlank
    private final String productName;

    public UpdateProductCommand(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }
}