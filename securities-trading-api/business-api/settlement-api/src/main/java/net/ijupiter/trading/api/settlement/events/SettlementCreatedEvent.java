package net.ijupiter.trading.api.settlement.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 清算创建事件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class SettlementCreatedEvent {
    /**
     * 清算编号
     */
    private String settlementCode;
    
    /**
     * 清算类型
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
     * 清算金额
     */
    private BigDecimal amount;
    
    /**
     * 手续费
     */
    private BigDecimal fee;
    
    /**
     * 印花税
     */
    private BigDecimal tax;
    
    /**
     * 操作员ID
     */
    private Long operatorId;
    
    /**
     * 事件时间
     */
    private LocalDateTime eventTime;
    
    /**
     * 备注
     */
    private String remark;
}