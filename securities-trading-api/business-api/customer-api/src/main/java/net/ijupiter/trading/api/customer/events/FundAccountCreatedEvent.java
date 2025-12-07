package net.ijupiter.trading.api.customer.events;

import lombok.Getter;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.enums.FundAccountType;

import java.time.LocalDateTime;

/**
 * 资金账户创建事件（包含银行卡信息）
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class FundAccountCreatedEvent {

    private final String accountId;
    private final String customerId;
    private final String accountName;
    private final FundAccountType accountType;
    private final AccountStatus status;
    
    // 银行卡信息
    private final String bankCardNumber;
    private final String bankCode;
    private final String bankName;
    private final String holderName;
    private final String currency;
    
    private final LocalDateTime createTime;
    private final Long timestamp;

    public FundAccountCreatedEvent(String accountId, String customerId, String accountName,
                                    FundAccountType accountType, AccountStatus status,
                                    String bankCardNumber, String bankCode, String bankName,
                                    String holderName, String currency) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.status = status;
        this.bankCardNumber = bankCardNumber;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.holderName = holderName;
        this.currency = currency;
        this.createTime = LocalDateTime.now();
        this.timestamp = System.currentTimeMillis();
    }
}