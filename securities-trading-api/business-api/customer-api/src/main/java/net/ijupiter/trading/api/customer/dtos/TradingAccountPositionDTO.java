package net.ijupiter.trading.api.customer.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易账户持仓数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TradingAccountPositionDTO extends BaseDTO<TradingAccountPositionDTO> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 交易账户ID
     */
    private String accountId;

    /**
     * 交易账户编号
     */
    private String accountCode;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 持仓份额
     */
    private BigDecimal positionShares;

    /**
     * 可用份额
     */
    private BigDecimal availableShares;

    /**
     * 冻结份额
     */
    private BigDecimal frozenShares;

    /**
     * 当日买入份额
     */
    private BigDecimal dailyBuyShares;

    /**
     * 当日卖出份额
     */
    private BigDecimal dailySellShares;

    /**
     * 当日净发生份额
     */
    private BigDecimal dailyNetShares;

    /**
     * 总持仓市值
     */
    private BigDecimal totalMarketValue;

    /**
     * 可用持仓市值
     */
    private BigDecimal availableMarketValue;

    /**
     * 冻结持仓市值
     */
    private BigDecimal frozenMarketValue;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;
}