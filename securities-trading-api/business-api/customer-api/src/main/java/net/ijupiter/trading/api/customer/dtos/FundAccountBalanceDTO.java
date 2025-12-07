package net.ijupiter.trading.api.customer.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户余额数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FundAccountBalanceDTO extends BaseDTO<FundAccountBalanceDTO> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资金账户ID
     */
    private String accountId;

    /**
     * 资金账户编号
     */
    private String accountCode;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 可用余额
     */
    private BigDecimal availableBalance;

    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;

    /**
     * 总余额
     */
    private BigDecimal totalBalance;

    /**
     * 当日入金金额
     */
    private BigDecimal dailyDepositAmount;

    /**
     * 当日出金金额
     */
    private BigDecimal dailyWithdrawAmount;

    /**
     * 当日净发生金额
     */
    private BigDecimal dailyNetAmount;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;
}