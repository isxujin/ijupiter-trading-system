package net.ijupiter.trading.api.settlement.commands;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * 开始结算命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class StartSettlementCommand {

    @NotBlank
    private final String settlementId;
    
    @NotBlank
    private final LocalDate settlementDate;

    public StartSettlementCommand(String settlementId, LocalDate settlementDate) {
        this.settlementId = settlementId;
        this.settlementDate = settlementDate;
    }
}