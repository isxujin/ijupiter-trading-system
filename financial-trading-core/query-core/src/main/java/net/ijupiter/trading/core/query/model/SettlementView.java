package net.ijupiter.trading.core.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 结算记录视图模型
 * 
 * @author ijupiter
 */
@Entity
@Table(name = "settlement_view")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementView {
    
    @Id
    private String settlementId;
    
    @Column(name = "order_id")
    private String orderId;
    
    @Column(name = "trade_id")
    private String tradeId;
    
    @Column(name = "customer_id")
    private String customerId;
    
    @Column(name = "account_id")
    private String accountId;
    
    @Column(name = "product_code")
    private String productCode;
    
    @Column(name = "settlement_date")
    private String settlementDate;
    
    @Column(name = "settlement_amount", precision = 19, scale = 4)
    private BigDecimal settlementAmount;
    
    @Column(name = "settlement_quantity", precision = 19, scale = 4)
    private BigDecimal settlementQuantity;
    
    @Column(name = "settlement_price", precision = 19, scale = 4)
    private BigDecimal settlementPrice;
    
    @Column(name = "commission", precision = 19, scale = 4)
    private BigDecimal commission;
    
    @Column(name = "tax", precision = 19, scale = 4)
    private BigDecimal tax;
    
    @Column(name = "other_fee", precision = 19, scale = 4)
    private BigDecimal otherFee;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "settlement_time")
    private LocalDateTime settlementTime;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}