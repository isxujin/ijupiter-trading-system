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
 * 产品视图模型
 * 
 * @author ijupiter
 */
@Entity
@Table(name = "product_view")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {
    
    @Id
    private String productId;
    
    @Column(name = "product_code")
    private String productCode;
    
    @Column(name = "product_name")
    private String productName;
    
    @Column(name = "product_type")
    private String productType;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "market")
    private String market;
    
    @Column(name = "currency")
    private String currency;
    
    @Column(name = "min_quantity", precision = 19, scale = 4)
    private BigDecimal minQuantity;
    
    @Column(name = "max_quantity", precision = 19, scale = 4)
    private BigDecimal maxQuantity;
    
    @Column(name = "quantity_precision")
    private Integer quantityPrecision;
    
    @Column(name = "price_precision")
    private Integer pricePrecision;
    
    @Column(name = "limit_up_price", precision = 19, scale = 4)
    private BigDecimal limitUpPrice;
    
    @Column(name = "limit_down_price", precision = 19, scale = 4)
    private BigDecimal limitDownPrice;
    
    @Column(name = "previous_close", precision = 19, scale = 4)
    private BigDecimal previousClose;
    
    @Column(name = "latest_price", precision = 19, scale = 4)
    private BigDecimal latestPrice;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}