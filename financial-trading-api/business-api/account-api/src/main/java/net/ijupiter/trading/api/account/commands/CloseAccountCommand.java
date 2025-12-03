package net.ijupiter.trading.api.account.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 关闭账户命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CloseAccountCommand {

    @NotBlank
    private final String accountId;
    
    private final String reason;

    public CloseAccountCommand(String accountId, String reason) {
        this.accountId = accountId;
        this.reason = reason;
    }
}