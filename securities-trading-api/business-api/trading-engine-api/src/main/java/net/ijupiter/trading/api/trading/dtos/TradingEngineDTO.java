package net.ijupiter.trading.api.trading.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易引擎DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TradingEngineDTO extends BaseDTO<TradingEngineDTO> {
    /**
     * 交易编号
     */
    private String tradeCode;
    
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
     * 交易金额
     */
    private BigDecimal amount;
    
    /**
     * 手续费
     */
    private BigDecimal fee;
    
    /**
     * 交易状态(1:待撮合,2:部分成交,3:全部成交,4:已撤销,5:已拒绝)
     */
    private Integer status;
    
    /**
     * 买方客户ID
     */
    private Long buyerCustomerId;
    
    /**
     * 卖方客户ID
     */
    private Long sellerCustomerId;
    
    /**
     * 撮合时间
     */
    private LocalDateTime matchTime;
    
    /**
     * 成交时间
     */
    private LocalDateTime executeTime;
    
    /**
     * 交易市场(1:主板,2:创业板,3:科创板)
     */
    private Integer market;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
}