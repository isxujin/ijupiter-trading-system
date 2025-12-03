package net.ijupiter.trading.api.fund.commands;

import lombok.Getter;
import net.ijupiter.trading.api.fund.enums.WithdrawType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 资金出金命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class WithdrawFundCommand {

    @NotBlank
    private final String fundAccountId;
    
    @NotBlank
    private final String transactionId;
    
    @NotNull
    @Positive
    private final BigDecimal amount;
    
    @NotNull
    private final WithdrawType withdrawType;
    
    private final String description;

    public WithdrawFundCommand(String fundAccountId, String transactionId,
                               BigDecimal amount, WithdrawType withdrawType, String description) {
        this.fundAccountId = fundAccountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.withdrawType = withdrawType;
        this.description = description;
    }
}