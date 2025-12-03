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
 * 订单视图模型
 * 
 * @author ijupiter
 */
@Entity
@Table(name = "order_view")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderView {
    
    @Id
    private String orderId;
    
    @Column(name = "customer_id")
    private String customerId;
    
    @Column(name = "account_id")
    private String accountId;
    
    @Column(name = "order_no")
    private String orderNo;
    
    @Column(name = "product_code")
    private String productCode;
    
    @Column(name = "product_name")
    private String productName;
    
    @Column(name = "order_type")
    private String orderType;
    
    @Column(name = "order_side")
    private String orderSide;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "price", precision = 19, scale = 4)
    private BigDecimal price;
    
    @Column(name = "quantity", precision = 19, scale = 4)
    private BigDecimal quantity;
    
    @Column(name = "executed_quantity", precision = 19, scale = 4)
    private BigDecimal executedQuantity;
    
    @Column(name = "executed_amount", precision = 19, scale = 4)
    private BigDecimal executedAmount;
    
    @Column(name = "avg_price", precision = 19, scale = 4)
    private BigDecimal avgPrice;
    
    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;
    
    @Column(name = "order_time")
    private LocalDateTime orderTime;
    
    @Column(name = "execute_time")
    private LocalDateTime executeTime;
    
    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}