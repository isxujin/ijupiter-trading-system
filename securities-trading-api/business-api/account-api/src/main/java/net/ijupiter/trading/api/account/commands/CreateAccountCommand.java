package net.ijupiter.trading.api.account.commands;

import lombok.Getter;
import net.ijupiter.trading.api.account.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 创建账户命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CreateAccountCommand {

    @NotBlank
    private final String accountId;
    
    @NotBlank
    private final String userId;
    
    @NotBlank
    private final String accountName;
    
    @NotNull
    private final AccountType accountType;

    public CreateAccountCommand(String accountId, String userId, String accountName, 
                               AccountType accountType) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountName = accountName;
        this.accountType = accountType;
    }
}