package net.ijupiter.trading.core.customer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 客户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "cust_customer")
public class CustomerEntity extends BaseEntity<CustomerEntity> {
    
    /**
     * 客户编号
     */
    @Column(name = "customer_code", nullable = false, unique = true, length = 50)
    private String customerCode;
    
    /**
     * 客户名称
     */
    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;
    
    /**
     * 客户类型(1:个人,2:机构)
     */
    @Column(name = "customer_type", nullable = false)
    private Integer customerType;
    
    /**
     * 证件类型(1:身份证,2:护照,3:营业执照,4:组织机构代码)
     */
    @Column(name = "id_type")
    private Integer idType;
    
    /**
     * 证件号码
     */
    @Column(name = "id_number", length = 50)
    private String idNumber;
    
    /**
     * 手机号码
     */
    @Column(name = "mobile", length = 20)
    private String mobile;
    
    /**
     * 电子邮箱
     */
    @Column(name = "email", length = 100)
    private String email;
    
    /**
     * 联系地址
     */
    @Column(name = "address", length = 200)
    private String address;
    
    /**
     * 风险等级(1:保守型,2:稳健型,3:进取型)
     */
    @Column(name = "risk_level")
    private Integer riskLevel;
    
    /**
     * 客户状态(1:正常,2:冻结,3:注销)
     */
    @Column(name = "status", nullable = false)
    private Integer status;
    
    /**
     * 开户日期
     */
    @Column(name = "open_date")
    private LocalDateTime openDate;
    
    /**
     * 生日
     */
    @Column(name = "birthday")
    private LocalDate birthday;
    
    /**
     * 性别(1:男,2:女)
     */
    @Column(name = "gender")
    private Integer gender;
    
    /**
     * 职业
     */
    @Column(name = "occupation", length = 100)
    private String occupation;
    
    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;
    

}