package net.ijupiter.trading.api.fund.commands;

import lombok.Getter;
import net.ijupiter.trading.api.fund.enums.DepositType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 资金入金命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class DepositFundCommand {

    @NotBlank
    private final String fundAccountId;
    
    @NotBlank
    private final String transactionId;
    
    @NotNull
    @Positive
    private final BigDecimal amount;
    
    @NotNull
    private final DepositType depositType;
    
    private final String description;

    public DepositFundCommand(String fundAccountId, String transactionId,
                              BigDecimal amount, DepositType depositType, String description) {
        this.fundAccountId = fundAccountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.depositType = depositType;
        this.description = description;
    }
}