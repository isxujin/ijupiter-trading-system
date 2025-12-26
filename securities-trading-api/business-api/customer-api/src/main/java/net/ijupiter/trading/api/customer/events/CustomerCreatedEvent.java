package net.ijupiter.trading.api.customer.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 客户创建事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerCreatedEvent {
    /**
     * 客户编号
     */
    private String customerCode;
    
    /**
     * 客户名称
     */
    private String customerName;
    
    /**
     * 客户类型(1:个人,2:机构)
     */
    private Integer customerType;
    
    /**
     * 证件类型(1:身份证,2:护照,3:营业执照)
     */
    private Integer idType;
    
    /**
     * 证件号码
     */
    private String idNumber;
    
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
     * 事件时间
     */
    private LocalDateTime eventTime;
    
    /**
     * 备注
     */
    private String remark;
}