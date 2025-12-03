package net.ijupiter.trading.api.product.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 停牌证券产品命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class SuspendProductCommand {

    @NotBlank
    private final String productId;
    
    private final String reason;

    public SuspendProductCommand(String productId, String reason) {
        this.productId = productId;
        this.reason = reason;
    }
}