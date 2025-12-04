package net.ijupiter.trading.api.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.account.enums.AccountType;
import net.ijupiter.trading.common.base.BaseDTOWithCustomId;

import java.time.LocalDateTime;

/**
 * 账户数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountDTO extends BaseDTOWithCustomId {

    private String accountId;
    private String userId;
    private String accountName;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private LocalDateTime closeTime;
    private String closeReason;

    /**
     * 是否处于正常状态
     * 
     * @return 是否正常
     */
    public boolean isNormal() {
        return AccountStatus.NORMAL.equals(accountStatus);
    }

    /**
     * 是否处于冻结状态
     * 
     * @return 是否冻结
     */
    public boolean isFrozen() {
        return AccountStatus.FROZEN.equals(accountStatus);
    }

    /**
     * 是否处于锁定状态
     * 
     * @return 是否锁定
     */
    public boolean isLocked() {
        return AccountStatus.LOCKED.equals(accountStatus);
    }

    /**
     * 是否已关闭
     * 
     * @return 是否关闭
     */
    public boolean isClosed() {
        return AccountStatus.CLOSED.equals(accountStatus);
    }

    /**
     * 是否已注销
     * 
     * @return 是否注销
     */
    public boolean isCancelled() {
        return AccountStatus.CANCELLED.equals(accountStatus);
    }

    /**
     * 是否可以进行交易
     * 
     * @return 是否可交易
     */
    public boolean canTrade() {
        return isNormal();
    }
}