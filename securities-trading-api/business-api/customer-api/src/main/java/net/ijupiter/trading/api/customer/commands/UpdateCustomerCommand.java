package net.ijupiter.trading.api.customer.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 更新客户信息命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class UpdateCustomerCommand {

    @NotBlank
    private final String customerId;
    
    private final String customerName;
    
    private final String phoneNumber;
    
    private final String email;
    
    private final String address;
    
    private final String riskLevel;

    public UpdateCustomerCommand(String customerId, String customerName, 
                               String phoneNumber, String email, 
                               String address, String riskLevel) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.riskLevel = riskLevel;
    }
}