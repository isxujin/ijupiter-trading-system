package net.ijupiter.trading.api.securities.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

/**
 * 证券转托管命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferSecuritiesCommand {
    /**
     * 转托管编号(聚合标识符)
     */
    @TargetAggregateIdentifier
    private String transferCode;
    
    /**
     * 账户编号
     */
    private String accountCode;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 转托管数量
     */
    private BigDecimal quantity;
    
    /**
     * 转托管类型(1:转出,2:转入)
     */
    private Integer transferType;
    
    /**
     * 转入券商ID
     */
    private String toBrokerId;
    
    /**
     * 转入券商名称
     */
    private String toBrokerName;
    
    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 备注
     */
    private String remark;
}