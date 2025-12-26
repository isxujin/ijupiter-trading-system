package net.ijupiter.trading.api.trading.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 执行交易命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ExecuteTradeCommand {
    /**
     * 交易编号
     */
    private String tradeCode;
    
    /**
     * 成交价格
     */
    private BigDecimal executePrice;
    
    /**
     * 成交数量
     */
    private BigDecimal executeQuantity;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
}