package net.ijupiter.trading.api.query.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 客户证券持仓信息查询DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CustomerSecuritiesPositionDTO extends BaseDTO<CustomerSecuritiesPositionDTO> {
    
    /**
     * 持仓ID
     */
    private Long positionId;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 账户ID
     */
    private Long accountId;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 证券类型 (1:股票, 2:债券, 3:基金)
     */
    private Integer securityType;
    
    /**
     * 持仓数量
     */
    private BigDecimal positionQuantity;
    
    /**
     * 可用数量
     */
    private BigDecimal availableQuantity;
    
    /**
     * 冻结数量
     */
    private BigDecimal frozenQuantity;
    
    /**
     * 成本价
     */
    private BigDecimal costPrice;
    
    /**
     * 当前价
     */
    private BigDecimal currentPrice;
    
    /**
     * 市值
     */
    private BigDecimal marketValue;
    
    /**
     * 成本金额
     */
    private BigDecimal costAmount;
    
    /**
     * 盈亏金额
     */
    private BigDecimal profitLossAmount;
    
    /**
     * 盈亏率
     */
    private BigDecimal profitLossRate;
    
    /**
     * 今日盈亏金额
     */
    private BigDecimal todayProfitLossAmount;
    
    /**
     * 今日盈亏率
     */
    private BigDecimal todayProfitLossRate;
    
    /**
     * 持仓天数
     */
    private Integer holdingDays;
    
    /**
     * 首次买入时间
     */
    private LocalDateTime firstBuyTime;
    
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
}