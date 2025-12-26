package net.ijupiter.trading.api.customer.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * 更新客户命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCustomerCommand {
    /**
     * 客户ID
     */
    @TargetAggregateIdentifier
    private String customerCode;
    
    /**
     * 客户名称
     */
    private String customerName;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 电子邮箱
     */
    private String email;
    
    /**
     * 联系地址
     */
    private String address;
    
    /**
     * 风险等级(1:低风险,2:中风险,3:高风险)
     */
    private Integer riskLevel;
    
    /**
     * 操作员ID
     */
    private String operatorId;
    
    /**
     * 备注
     */
    private String remark;
}