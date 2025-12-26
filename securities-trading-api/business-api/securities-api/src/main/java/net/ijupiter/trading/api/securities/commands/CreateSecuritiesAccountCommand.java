package net.ijupiter.trading.api.securities.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

/**
 * 创建证券账户命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSecuritiesAccountCommand {
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
     * 初始资金
     */
    private BigDecimal initialFunds;
    
    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 备注
     */
    private String remark;
}