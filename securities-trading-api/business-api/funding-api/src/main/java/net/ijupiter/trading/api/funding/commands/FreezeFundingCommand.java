package net.ijupiter.trading.api.funding.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

/**
 * 冻结资金命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreezeFundingCommand {
    /**
     * 账户编号(聚合标识符)
     */
    @TargetAggregateIdentifier
    private String accountCode;
    
    /**
     * 冻结金额
     */
    private BigDecimal amount;
    
    /**
     * 冻结原因
     */
    private String reason;
    
    /**
     * 操作员ID
     */
    private String operatorId;
}