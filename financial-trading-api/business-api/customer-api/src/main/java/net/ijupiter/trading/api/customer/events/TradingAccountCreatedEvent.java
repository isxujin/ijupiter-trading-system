package net.ijupiter.trading.api.customer.events;

import lombok.Getter;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.enums.TradingAccountType;

import java.time.LocalDateTime;

/**
 * 交易账户创建事件（包含交易所账号信息）
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class TradingAccountCreatedEvent {

    private final String accountId;
    private final String customerId;
    private final String accountName;
    private final String exchangeCode;
    private final String exchangeName;
    private final String exchangeAccountNumber;
    private final TradingAccountType accountType;
    private final AccountStatus status;
    private final String tradingProduct;
    
    // 交易所账号信息
    private final String tradingPassword;
    private final String fundPassword;
    private final String apiKey;
    private final String apiSecret;
    
    private final LocalDateTime createTime;
    private final Long timestamp;

    public TradingAccountCreatedEvent(String accountId, String customerId, String accountName,
                                      String exchangeCode, String exchangeName,
                                      String exchangeAccountNumber, TradingAccountType accountType,
                                      AccountStatus status, String tradingProduct,
                                      String tradingPassword, String fundPassword,
                                      String apiKey, String apiSecret) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountName = accountName;
        this.exchangeCode = exchangeCode;
        this.exchangeName = exchangeName;
        this.exchangeAccountNumber = exchangeAccountNumber;
        this.accountType = accountType;
        this.status = status;
        this.tradingProduct = tradingProduct;
        this.tradingPassword = tradingPassword;
        this.fundPassword = fundPassword;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.createTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}