package net.ijupiter.trading.api.settlement.commands;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 创建清算命令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CreateSettlementCommand {
    /**
     * 清算类型(1:资金清算,2:证券清算,3:衍生品清算)
     */
    private Integer settlementType;
    
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
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 清算数量
     */
    private BigDecimal quantity;
    
    /**
     * 清算价格
     */
    private BigDecimal price;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 备注
     */
    private String remark;
}