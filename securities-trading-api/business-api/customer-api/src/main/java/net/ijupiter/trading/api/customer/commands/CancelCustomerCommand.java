package net.ijupiter.trading.api.customer.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 注销客户命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CancelCustomerCommand {

    @NotBlank
    private final String customerId;
    
    @NotBlank
    private final String reason;

    public CancelCustomerCommand(String customerId, String reason) {
        this.customerId = customerId;
        this.reason = reason;
    }
}