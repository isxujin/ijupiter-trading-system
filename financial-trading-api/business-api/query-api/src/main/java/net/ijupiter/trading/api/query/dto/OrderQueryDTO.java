package net.ijupiter.trading.api.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单查询DTO
 * 
 * @author ijupiter
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderQueryDTO {
    
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
     * 订单编号
     */
    private String orderNo;
    
    /**
     * 产品代码
     */
    private String productCode;
    
    /**
     * 产品名称
     */
    private String productName;
    
    /**
     * 订单类型
     */
    private String orderType;
    
    /**
     * 订单方向
     */
    private String orderSide;
    
    /**
     * 订单状态
     */
    private String status;
    
    /**
     * 委托价格
     */
    private BigDecimal price;
    
    /**
     * 委托数量
     */
    private BigDecimal quantity;
    
    /**
     * 成交数量
     */
    private BigDecimal executedQuantity;
    
    /**
     * 成交金额
     */
    private BigDecimal executedAmount;
    
    /**
     * 平均成交价格
     */
    private BigDecimal avgPrice;
    
    /**
     * 订单金额
     */
    private BigDecimal amount;
    
    /**
     * 委托时间
     */
    private LocalDateTime orderTime;
    
    /**
     * 成交时间
     */
    private LocalDateTime executeTime;
    
    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}