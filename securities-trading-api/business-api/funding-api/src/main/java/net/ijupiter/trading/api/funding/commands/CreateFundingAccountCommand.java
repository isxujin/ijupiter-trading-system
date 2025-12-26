package net.ijupiter.trading.api.funding.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

/**
 * 创建资金账户命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFundingAccountCommand {
    /**
     * 账户编号(聚合标识符)
     */
    @TargetAggregateIdentifier
    private String accountCode;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 客户编号
     */
    private String customerCode;
    
    /**
     * 账户名称
     */
    private String accountName;
    
    /**
     * 初始余额
     */
    private BigDecimal initialBalance;
    
    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 备注
     */
    private String remark;
}