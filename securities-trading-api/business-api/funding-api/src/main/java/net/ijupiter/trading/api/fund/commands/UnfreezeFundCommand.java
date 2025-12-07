package net.ijupiter.trading.api.fund.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 资金解冻命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class UnfreezeFundCommand {

    @NotBlank
    private final String fundAccountId;
    
    @NotBlank
    private final String transactionId;
    
    @NotNull
    @Positive
    private final BigDecimal amount;
    
    private final String description;

    public UnfreezeFundCommand(String fundAccountId, String transactionId, 
                             BigDecimal amount, String description) {
        this.fundAccountId = fundAccountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.description = description;
    }
}