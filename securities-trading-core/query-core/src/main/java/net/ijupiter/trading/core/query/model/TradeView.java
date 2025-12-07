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
 * 成交记录视图模型
 * 
 * @author ijupiter
 */
@Entity
@Table(name = "trade_view")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeView {
    
    @Id
    private String tradeId;
    
    @Column(name = "order_id")
    private String orderId;
    
    @Column(name = "customer_id")
    private String customerId;
    
    @Column(name = "account_id")
    private String accountId;
    
    @Column(name = "product_code")
    private String productCode;
    
    @Column(name = "product_name")
    private String productName;
    
    @Column(name = "quantity", precision = 19, scale = 4)
    private BigDecimal quantity;
    
    @Column(name = "price", precision = 19, scale = 4)
    private BigDecimal price;
    
    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;
    
    @Column(name = "side")
    private String side;
    
    @Column(name = "market")
    private String market;
    
    @Column(name = "trade_time")
    private LocalDateTime tradeTime;
    
    @Column(name = "trade_no")
    private String tradeNo;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
}