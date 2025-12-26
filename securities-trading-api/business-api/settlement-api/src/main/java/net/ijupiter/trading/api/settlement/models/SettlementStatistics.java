package net.ijupiter.trading.api.settlement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 清算统计信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SettlementStatistics {
    /**
     * 总清算数
     */
    private long totalSettlements;
    
    /**
     * 待清算数
     */
    private long pendingSettlements;
    
    /**
     * 清算中数量
     */
    private long processingSettlements;
    
    /**
     * 已清算数
     */
    private long completedSettlements;
    
    /**
     * 清算失败数
     */
    private long failedSettlements;
    
    /**
     * 总清算金额
     */
    private BigDecimal totalAmount;
    
    /**
     * 总手续费
     */
    private BigDecimal totalFee;
    
    /**
     * 总印花税
     */
    private BigDecimal totalTax;
    
    /**
     * 今日清算数
     */
    private long todaySettlements;
    
    /**
     * 今日清算金额
     */
    private BigDecimal todaySettlementAmount;
}