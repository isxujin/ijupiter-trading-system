package net.ijupiter.trading.api.fund.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.fund.enums.DepositType;
import net.ijupiter.trading.api.fund.enums.FundAccountStatus;
import net.ijupiter.trading.api.fund.enums.FundTransactionType;
import net.ijupiter.trading.api.fund.enums.WithdrawType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金交易数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundTransactionDTO {

    private String transactionId;
    private String fundAccountId;
    private String accountId;
    private FundTransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balance;
    private BigDecimal frozenAmount;
    private DepositType depositType;
    private WithdrawType withdrawType;
    private String description;
    private LocalDateTime transactionTime;
    private FundAccountStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /**
     * 是否为入金交易
     * 
     * @return 是否为入金交易
     */
    public boolean isDeposit() {
        return FundTransactionType.DEPOSIT.equals(transactionType);
    }

    /**
     * 是否为出金交易
     * 
     * @return 是否为出金交易
     */
    public boolean isWithdraw() {
        return FundTransactionType.WITHDRAW.equals(transactionType);
    }

    /**
     * 是否为冻结交易
     * 
     * @return 是否为冻结交易
     */
    public boolean isFreeze() {
        return FundTransactionType.FREEZE.equals(transactionType);
    }

    /**
     * 是否为解冻交易
     * 
     * @return 是否为解冻交易
     */
    public boolean isUnfreeze() {
        return FundTransactionType.UNFREEZE.equals(transactionType);
    }
}