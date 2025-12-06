package net.ijupiter.trading.api.customer.commands;

import lombok.Getter;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;

import jakarta.validation.constraints.NotBlank;

/**
 * 创建客户命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CreateCustomerCommand {

    @NotBlank
    private final String customerId;
    
    @NotBlank
    private final String customerCode;
    
    @NotBlank
    private final String customerName;
    
    @NotBlank
    private final String idCardNumber;
    
    @NotBlank
    private final String phoneNumber;
    
    private final String email;
    
    private final String address;
    
    @NotBlank
    private final String riskLevel;

    public CreateCustomerCommand(String customerId, String customerCode, String customerName, 
                               String idCardNumber, String phoneNumber, String email, 
                               String address, String riskLevel) {
        this.customerId = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.idCardNumber = idCardNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.riskLevel = riskLevel;
    }
}