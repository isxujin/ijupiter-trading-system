package net.ijupiter.trading.api.query.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 客户每日证券收益信息查询DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CustomerDailySecuritiesProfitDTO extends BaseDTO<CustomerDailySecuritiesProfitDTO> {
    
    /**
     * 记录ID
     */
    private Long recordId;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 持仓数量
     */
    private BigDecimal positionQuantity;
    
    /**
     * 开盘价
     */
    private BigDecimal openingPrice;
    
    /**
     * 最高价
     */
    private BigDecimal highestPrice;
    
    /**
     * 最低价
     */
    private BigDecimal lowestPrice;
    
    /**
     * 收盘价
     */
    private BigDecimal closingPrice;
    
    /**
     * 前一日收盘价
     */
    private BigDecimal previousClosePrice;
    
    /**
     * 日涨跌额
     */
    private BigDecimal dailyChangeAmount;
    
    /**
     * 日涨跌幅
     */
    private BigDecimal dailyChangeRate;
    
    /**
     * 当日盈亏金额
     */
    private BigDecimal dailyProfitLossAmount;
    
    /**
     * 当日盈亏率
     */
    private BigDecimal dailyProfitLossRate;
    
    /**
     * 累计盈亏金额
     */
    private BigDecimal totalProfitLossAmount;
    
    /**
     * 累计盈亏率
     */
    private BigDecimal totalProfitLossRate;
    
    /**
     * 交易日期
     */
    private LocalDate tradeDate;
    
    /**
     * 创建时间
     */
    private java.time.LocalDateTime createTime;
}