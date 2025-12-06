package net.ijupiter.trading.api.customer.commands;

import lombok.Getter;
import net.ijupiter.trading.api.customer.enums.TradingAccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 创建交易账户命令（包含交易所账号信息）
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CreateTradingAccountCommand {

    @NotBlank
    private final String accountId;
    
    @NotBlank
    private final String customerId;
    
    @NotBlank
    private final String accountName;
    
    @NotBlank
    private final String exchangeCode;
    
    @NotBlank
    private final String exchangeName;
    
    @NotBlank
    private final String exchangeAccountNumber;
    
    @NotNull
    private final TradingAccountType accountType;
    
    @NotBlank
    private final String tradingProduct;
    
    // 交易所账号信息
    private final String tradingPassword;
    private final String fundPassword;
    private final String apiKey;
    private final String apiSecret;

    public CreateTradingAccountCommand(String accountId, String customerId, String accountName, 
                                      String exchangeCode, String exchangeName, 
                                      String exchangeAccountNumber, TradingAccountType accountType,
                                      String tradingProduct, String tradingPassword, 
                                      String fundPassword, String apiKey, String apiSecret) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountName = accountName;
        this.exchangeCode = exchangeCode;
        this.exchangeName = exchangeName;
        this.exchangeAccountNumber = exchangeAccountNumber;
        this.accountType = accountType;
        this.tradingProduct = tradingProduct;
        this.tradingPassword = tradingPassword;
        this.fundPassword = fundPassword;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }
}