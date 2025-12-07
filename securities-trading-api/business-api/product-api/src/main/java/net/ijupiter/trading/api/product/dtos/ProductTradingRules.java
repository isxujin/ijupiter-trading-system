package net.ijupiter.trading.api.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * 产品交易规则
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTradingRules {

    private String productId;
    
    // 交易时间
    private LocalTime tradingStartTime;
    private LocalTime tradingEndTime;
    private LocalTime lunchStartTime;
    private LocalTime lunchEndTime;
    
    // 涨跌幅限制
    private BigDecimal dailyUpLimit;
    private BigDecimal dailyDownLimit;
    
    // T+N 规则
    private Integer tPlusN;
    
    // 最小交易金额
    private BigDecimal minTradeAmount;
    
    // 最大交易金额
    private BigDecimal maxTradeAmount;
    
    // 交易手续费率
    private BigDecimal commissionRate;
    
    // 印花税率
    private BigDecimal stampTaxRate;
    
    // 其他费用率
    private BigDecimal otherFeeRate;
    
    // 是否允许做空
    private boolean shortSellingAllowed;
    
    // 是否允许T+0交易
    private boolean tPlusZeroAllowed;
    
    // 连续涨跌停限制
    private Integer continuousUpDownLimit;
    
    // 委托数量限制
    private Integer maxOrderCount;
    
    // 委托金额限制
    private BigDecimal maxOrderAmount;
}