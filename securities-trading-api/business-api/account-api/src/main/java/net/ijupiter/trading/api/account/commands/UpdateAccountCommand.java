package net.ijupiter.trading.api.account.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

/**
 * 更新账户命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class UpdateAccountCommand {

    @NotBlank
    private final String accountId;
    
    @NotBlank
    private final String accountName;

    public UpdateAccountCommand(String accountId, String accountName) {
        this.accountId = accountId;
        this.accountName = accountName;
    }
}