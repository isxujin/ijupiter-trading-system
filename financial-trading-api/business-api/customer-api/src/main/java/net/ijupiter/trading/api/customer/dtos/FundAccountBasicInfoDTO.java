package net.ijupiter.trading.api.customer.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.enums.FundAccountType;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 资金账户基本信息数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FundAccountBasicInfoDTO extends BaseDTO<FundAccountBasicInfoDTO> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资金账户编号
     */
    private String accountCode;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户状态
     */
    private AccountStatus status;

    /**
     * 资金账户类型
     */
    private FundAccountType accountType;

    /**
     * 银行卡号
     */
    private String bankCardNumber;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 持卡人姓名
     */
    private String holderName;

    /**
     * 币种
     */
    private String currency;

    /**
     * 开户时间
     */
    private LocalDateTime openDate;

    /**
     * 关闭时间
     */
    private LocalDateTime closeDate;

    /**
     * 关闭原因
     */
    private String closeReason;


}