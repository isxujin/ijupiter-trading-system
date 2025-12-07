package net.ijupiter.trading.api.customer.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 客户数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CustomerDTO extends BaseDTO<CustomerDTO> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户状态
     */
    private CustomerStatus status;

    /**
     * 身份证号
     */
    private String idCardNumber;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 客户风险等级
     */
    private String riskLevel;

    /**
     * 是否激活
     */
    private Boolean isActive;

    /**
     * 开户时间
     */
    private LocalDateTime openDate;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 注销时间
     */
    private LocalDateTime closeDate;

    /**
     * 注销原因
     */
    private String closeReason;

    /**
     * 冻结时间
     */
    private LocalDateTime freezeDate;

    /**
     * 冻结原因
     */
    private String freezeReason;


}