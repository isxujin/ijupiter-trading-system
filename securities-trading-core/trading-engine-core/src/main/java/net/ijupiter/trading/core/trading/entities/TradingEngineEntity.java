package net.ijupiter.trading.core.trading.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易引擎实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "trad_trading_engine")
public class TradingEngineEntity extends BaseEntity<TradingEngineEntity> {
    
    /**
     * 交易编号
     */
    @Column(name = "trade_code", nullable = false, unique = true, length = 50)
    private String tradeCode;
    
    /**
     * 订单编号
     */
    @Column(name = "order_code", length = 50)
    private String orderCode;
    
    /**
     * 交易类型(1:买入,2:卖出)
     */
    @Column(name = "trade_type", nullable = false)
    private Integer tradeType;
    
    /**
     * 客户ID
     */
    @Column(name = "customer_id")
    private Long customerId;
    
    /**
     * 客户编号
     */
    @Column(name = "customer_code", length = 50)
    private String customerCode;
    
    /**
     * 证券代码
     */
    @Column(name = "security_code", length = 20)
    private String securityCode;
    
    /**
     * 证券名称
     */
    @Column(name = "security_name", length = 100)
    private String securityName;
    
    /**
     * 交易数量
     */
    @Column(name = "quantity", precision = 20, scale = 8)
    private BigDecimal quantity;
    
    /**
     * 交易价格
     */
    @Column(name = "price", precision = 20, scale = 8)
    private BigDecimal price;
    
    /**
     * 交易金额
     */
    @Column(name = "amount", precision = 20, scale = 2)
    private BigDecimal amount;
    
    /**
     * 手续费
     */
    @Column(name = "fee", precision = 20, scale = 2)
    private BigDecimal fee;
    
    /**
     * 交易状态(1:待撮合,2:部分成交,3:全部成交,4:已撤销,5:已拒绝)
     */
    @Column(name = "status", nullable = false)
    private Integer status;
    
    /**
     * 买方客户ID
     */
    @Column(name = "buyer_customer_id")
    private Long buyerCustomerId;
    
    /**
     * 卖方客户ID
     */
    @Column(name = "seller_customer_id")
    private Long sellerCustomerId;
    
    /**
     * 撮合时间
     */
    @Column(name = "match_time")
    private LocalDateTime matchTime;
    
    /**
     * 成交时间
     */
    @Column(name = "execute_time")
    private LocalDateTime executeTime;
    
    /**
     * 交易市场(1:主板,2:创业板,3:科创板)
     */
    @Column(name = "market")
    private Integer market;
    
    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;
    
    // ==================== 业务方法 ====================
    
    /**
     * 撮合交易
     */
    public void matchTrade(Long buyerCustomerId, Long sellerCustomerId) {
        if (this.status != 1) {
            throw new IllegalStateException("只有待撮合状态的交易才能撮合");
        }
        this.status = 2; // 部分成交状态
        this.buyerCustomerId = buyerCustomerId;
        this.sellerCustomerId = sellerCustomerId;
        this.matchTime = LocalDateTime.now();
    }
    
    /**
     * 执行交易
     */
    public void executeTrade() {
        if (this.status != 2) {
            throw new IllegalStateException("只有部分成交状态的交易才能执行");
        }
        this.status = 3; // 全部成交状态
        this.executeTime = LocalDateTime.now();
    }
    
    /**
     * 取消交易
     */
    public void cancelTrade() {
        if (this.status == 3) {
            throw new IllegalStateException("已成交的交易无法取消");
        }
        if (this.status == 4) {
            throw new IllegalStateException("交易已经取消");
        }
        this.status = 4; // 已撤销状态
    }
    
    /**
     * 拒绝交易
     */
    public void rejectTrade() {
        if (this.status == 3) {
            throw new IllegalStateException("已成交的交易无法拒绝");
        }
        if (this.status == 4) {
            throw new IllegalStateException("已撤销的交易无法拒绝");
        }
        this.status = 5; // 已拒绝状态
    }
    
    /**
     * 计算交易金额
     */
    public void calculateAmount() {
        if (this.price != null && this.quantity != null) {
            this.amount = this.price.multiply(this.quantity);
        }
    }

}