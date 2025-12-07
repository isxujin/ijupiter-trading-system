package net.ijupiter.trading.api.customer.commands;

import lombok.Getter;
import net.ijupiter.trading.api.customer.enums.FundAccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 创建资金账户命令（包含银行卡信息）
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CreateFundAccountCommand {

    @NotBlank
    private final String accountId;
    
    @NotBlank
    private final String customerId;
    
    @NotBlank
    private final String accountName;
    
    @NotNull
    private final FundAccountType accountType;
    
    // 银行卡信息
    @NotBlank
    private final String bankCardNumber;
    
    @NotBlank
    private final String bankCode;
    
    @NotBlank
    private final String bankName;
    
    @NotBlank
    private final String holderName;
    
    @NotBlank
    private final String currency;

    public CreateFundAccountCommand(String accountId, String customerId, String accountName, 
                                  FundAccountType accountType, String bankCardNumber, 
                                  String bankCode, String bankName, String holderName, 
                                  String currency) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.bankCardNumber = bankCardNumber;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.holderName = holderName;
        this.currency = currency;
    }
}