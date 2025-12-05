package net.ijupiter.trading.api.query.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 结算记录查询DTO
 * 
 * @author ijupiter
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementQueryDTO {
    
    /**
     * 结算ID
     */
    private String settlementId;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 成交ID
     */
    private String tradeId;
    
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
     * 结算日期
     */
    private String settlementDate;
    
    /**
     * 结算金额
     */
    private BigDecimal settlementAmount;
    
    /**
     * 结算数量
     */
    private BigDecimal settlementQuantity;
    
    /**
     * 结算价格
     */
    private BigDecimal settlementPrice;
    
    /**
     * 手续费
     */
    private BigDecimal commission;
    
    /**
     * 印花税
     */
    private BigDecimal tax;
    
    /**
     * 其他费用
     */
    private BigDecimal otherFee;
    
    /**
     * 结算状态
     */
    private String status;
    
    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}