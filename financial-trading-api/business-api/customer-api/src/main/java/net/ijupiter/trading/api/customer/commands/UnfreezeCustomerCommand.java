package net.ijupiter.trading.api.customer.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 解冻客户命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class UnfreezeCustomerCommand {

    @NotBlank
    private final String customerId;
    
    @NotBlank
    private final String reason;

    public UnfreezeCustomerCommand(String customerId, String reason) {
        this.customerId = customerId;
        this.reason = reason;
    }
}