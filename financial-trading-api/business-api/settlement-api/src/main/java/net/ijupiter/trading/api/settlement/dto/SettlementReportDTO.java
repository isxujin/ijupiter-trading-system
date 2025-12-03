package net.ijupiter.trading.api.settlement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 结算报告数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementReportDTO {

    private String settlementId;
    private LocalDate settlementDate;
    
    // 交易统计
    private Long totalTradeCount;
    private BigDecimal totalTradeAmount;
    
    // 持仓统计
    private Long totalPositionCount;
    private BigDecimal totalPositionValue;
    
    // 资金统计
    private BigDecimal totalFundIn;
    private BigDecimal totalFundOut;
    private BigDecimal totalFundBalance;
    
    // 手续费统计
    private BigDecimal totalCommission;
    private BigDecimal totalStampTax;
    private BigDecimal totalOtherFees;
    
    // 风险指标
    private BigDecimal systemRiskLevel;
    private BigDecimal concentrationRatio;
    
    // 产品交易明细
    private List<ProductSettlementDTO> productSettlements;
    
    /**
     * 产品结算明细
     */
    @Data
    @Builder
    public static class ProductSettlementDTO {
        private String productId;
        private String productCode;
        private String productName;
        private Long tradeCount;
        private BigDecimal tradeAmount;
        private Long buyCount;
        private BigDecimal buyAmount;
        private Long sellCount;
        private BigDecimal sellAmount;
        private BigDecimal netAmount;
        private BigDecimal turnoverRate;
        private BigDecimal averagePrice;
        private BigDecimal highestPrice;
        private BigDecimal lowestPrice;
        private BigDecimal openingPrice;
        private BigDecimal closingPrice;
        private BigDecimal priceChange;
        private BigDecimal priceChangeRate;
    }
}