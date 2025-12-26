package net.ijupiter.trading.api.customer.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * 冻结客户命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreezeCustomerCommand {
    /**
     * 客户ID
     */
    @TargetAggregateIdentifier
    private String customerCode;
    
    /**
     * 冻结原因
     */
    private String reason;
    
    /**
     * 操作员ID
     */
    private String operatorId;
}