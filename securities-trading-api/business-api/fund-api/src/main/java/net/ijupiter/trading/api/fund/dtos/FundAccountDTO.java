package net.ijupiter.trading.api.fund.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.fund.enums.FundAccountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundAccountDTO {

    private String fundAccountId;
    private String accountId;
    private BigDecimal balance;
    private BigDecimal frozenAmount;
    private BigDecimal availableBalance;
    private FundAccountStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /**
     * 是否处于正常状态
     * 
     * @return 是否正常
     */
    public boolean isNormal() {
        return FundAccountStatus.NORMAL.equals(status);
    }

    /**
     * 是否处于冻结状态
     * 
     * @return 是否冻结
     */
    public boolean isFrozen() {
        return FundAccountStatus.FROZEN.equals(status);
    }

    /**
     * 是否已注销
     * 
     * @return 是否注销
     */
    public boolean isCancelled() {
        return FundAccountStatus.CANCELLED.equals(status);
    }

    /**
     * 是否可以进行交易
     * 
     * @return 是否可交易
     */
    public boolean canTrade() {
        return isNormal();
    }

    /**
     * 计算可用余额
     * 
     * @return 可用余额
     */
    public BigDecimal calculateAvailableBalance() {
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        if (frozenAmount == null) {
            frozenAmount = BigDecimal.ZERO;
        }
        return balance.subtract(frozenAmount);
    }
}