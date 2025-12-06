package net.ijupiter.trading.api.customer.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 冻结客户命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class FreezeCustomerCommand {

    @NotBlank
    private final String customerId;
    
    @NotBlank
    private final String reason;

    public FreezeCustomerCommand(String customerId, String reason) {
        this.customerId = customerId;
        this.reason = reason;
    }
}