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
 * 客户交易流水查询DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CustomerTransactionSummaryDTO extends BaseDTO<CustomerTransactionSummaryDTO> {
    
    /**
     * 交易ID
     */
    private Long transactionId;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 交易编号
     */
    private String transactionCode;
    
    /**
     * 订单编号
     */
    private String orderCode;
    
    /**
     * 交易类型 (1:买入, 2:卖出)
     */
    private Integer transactionType;
    
    /**
     * 交易市场 (1:上海, 2:深圳)
     */
    private Integer market;
    
    /**
     * 证券代码
     */
    private String securityCode;
    
    /**
     * 证券名称
     */
    private String securityName;
    
    /**
     * 交易价格
     */
    private BigDecimal price;
    
    /**
     * 交易数量
     */
    private BigDecimal quantity;
    
    /**
     * 交易金额
     */
    private BigDecimal amount;
    
    /**
     * 手续费
     */
    private BigDecimal commission;
    
    /**
     * 印花税
     */
    private BigDecimal tax;
    
    /**
     * 交易状态 (1:待处理, 2:已成交, 3:已取消)
     */
    private Integer status;
    
    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;
    
    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;
    
    /**
     * 账户ID
     */
    private Long accountId;
    
    /**
     * 账户类型 (1:资金账户, 2:证券账户)
     */
    private Integer accountType;
}