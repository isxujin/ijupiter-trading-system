package net.ijupiter.trading.api.fund.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 创建资金账户命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CreateFundAccountCommand {

    @NotBlank
    private final String fundAccountId;
    
    @NotBlank
    private final String accountId;
    
    @NotNull
    private final BigDecimal initialBalance;

    public CreateFundAccountCommand(String fundAccountId, String accountId, BigDecimal initialBalance) {
        this.fundAccountId = fundAccountId;
        this.accountId = accountId;
        this.initialBalance = initialBalance;
    }
}