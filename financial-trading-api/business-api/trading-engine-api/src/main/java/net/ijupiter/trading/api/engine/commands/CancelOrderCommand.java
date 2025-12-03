package net.ijupiter.trading.api.engine.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 取消订单命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CancelOrderCommand {

    @NotBlank
    private final String orderId;
    
    private final String reason;

    public CancelOrderCommand(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }
}