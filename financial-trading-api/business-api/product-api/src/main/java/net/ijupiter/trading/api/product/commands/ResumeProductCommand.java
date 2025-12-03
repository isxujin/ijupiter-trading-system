package net.ijupiter.trading.api.product.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 复牌证券产品命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class ResumeProductCommand {

    @NotBlank
    private final String productId;
    
    private final String reason;

    public ResumeProductCommand(String productId, String reason) {
        this.productId = productId;
        this.reason = reason;
    }
}