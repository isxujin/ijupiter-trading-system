package net.ijupiter.trading.api.trading.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 交易统计信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TradingStatistics {
    /**
     * 总交易数
     */
    private long totalTrades;
    
    /**
     * 待撮合数
     */
    private long pendingTrades;
    
    /**
     * 部分成交数
     */
    private long partialTrades;
    
    /**
     * 全部成交数
     */
    private long completedTrades;
    
    /**
     * 已撤销数
     */
    private long cancelledTrades;
    
    /**
     * 总交易金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 总手续费
     */
    private BigDecimal totalFee;
    
    /**
     * 买入交易数
     */
    private long buyTrades;
    
    /**
     * 卖出交易数
     */
    private long sellTrades;
    
    /**
     * 今日交易数
     */
    private long todayTrades;
    
    /**
     * 今日交易金额
     */
    private BigDecimal todayTradeAmount;
}