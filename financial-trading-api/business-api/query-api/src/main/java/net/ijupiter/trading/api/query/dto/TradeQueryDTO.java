package net.ijupiter.trading.api.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成交记录查询DTO
 * 
 * @author ijupiter
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeQueryDTO {
    
    /**
     * 成交ID
     */
    private String tradeId;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 客户ID
     */
    private String customerId;
    
    /**
     * 账户ID
     */
    private String accountId;
    
    /**
     * 产品代码
     */
    private String productCode;
    
    /**
     * 产品名称
     */
    private String productName;
    
    /**
     * 成交数量
     */
    private BigDecimal quantity;
    
    /**
     * 成交价格
     */
    private BigDecimal price;
    
    /**
     * 成交金额
     */
    private BigDecimal amount;
    
    /**
     * 成交方向
     */
    private String side;
    
    /**
     * 交易市场
     */
    private String market;
    
    /**
     * 成交时间
     */
    private LocalDateTime tradeTime;
    
    /**
     * 交易编号
     */
    private String tradeNo;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}