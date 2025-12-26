package net.ijupiter.trading.api.trading.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 撮合交易命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class MatchTradeCommand {
    /**
     * 交易编号
     */
    private String tradeCode;
    
    /**
     * 买方客户ID
     */
    private Long buyerCustomerId;
    
    /**
     * 卖方客户ID
     */
    private Long sellerCustomerId;
    
    /**
     * 撮合价格
     */
    private BigDecimal matchPrice;
    
    /**
     * 撮合数量
     */
    private BigDecimal matchQuantity;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
}