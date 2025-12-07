package net.ijupiter.trading.api.customer.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.enums.TradingAccountType;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 交易账户基本信息数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TradingAccountDTO extends BaseDTO<TradingAccountDTO> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 交易账户编号
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
     * 交易账户类型
     */
    private TradingAccountType accountType;

    /**
     * 交易所代码
     */
    private String exchangeCode;

    /**
     * 交易所名称
     */
    private String exchangeName;

    /**
     * 交易所账号
     */
    private String exchangeAccountNumber;

    /**
     * 交易密码（加密存储）
     */
    private String tradingPassword;

    /**
     * 资金密码（加密存储）
     */
    private String fundPassword;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * API密钥密钥
     */
    private String apiSecret;

    /**
     * 交易产品品种
     */
    private String tradingProduct;

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