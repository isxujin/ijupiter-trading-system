package net.ijupiter.trading.api.funding.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

/**
 * 资金转账命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferFundingCommand {
    /**
     * 转账编号(聚合标识符)
     */
    @TargetAggregateIdentifier
    private String transferCode;
    
    /**
     * 转出账户编号
     */
    private String fromAccountCode;
    
    /**
     * 转入账户编号
     */
    private String toAccountCode;
    
    /**
     * 转账金额
     */
    private BigDecimal amount;
    
    /**
     * 转账类型(1:内部转账,2:外部转账)
     */
    private Integer transferType;
    
    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 备注
     */
    private String remark;
}