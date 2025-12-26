package net.ijupiter.trading.api.customer.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.time.LocalDateTime;

/**
 * 客户DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CustomerDTO extends BaseDTO<CustomerDTO> {
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
     * 客户状态(1:正常,2:冻结,3:注销)
     */
    private Integer status;
    
    /**
     * 风险等级(1:低风险,2:中风险,3:高风险)
     */
    private Integer riskLevel;
    
    /**
     * 开户日期
     */
    private LocalDateTime openDate;
    
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 备注
     */
    private String remark;
}