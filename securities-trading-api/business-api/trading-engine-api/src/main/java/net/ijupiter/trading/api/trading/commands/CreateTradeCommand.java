package net.ijupiter.trading.api.trading.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 创建交易命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CreateTradeCommand {
    /**
     * 订单编号
     */
    private String orderCode;
    
    /**
     * 交易类型(1:买入,2:卖出)
     */
    private Integer tradeType;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 客户编号
     */
    private String customerCode;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 交易数量
     */
    private BigDecimal quantity;
    
    /**
     * 交易价格
     */
    private BigDecimal price;
    
    /**
     * 交易市场(1:主板,2:创业板,3:科创板)
     */
    private Integer market;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 备注
     */
    private String remark;
}