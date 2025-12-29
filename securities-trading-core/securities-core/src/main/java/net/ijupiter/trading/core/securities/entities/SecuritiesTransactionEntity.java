package net.ijupiter.trading.core.securities.entities;

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
 * 证券变动流水实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "secu_securities_transaction")
public class SecuritiesTransactionEntity extends BaseEntity<SecuritiesTransactionEntity> {
    
    /**
     * 交易编号
     */
    @Column(name = "transaction_code", nullable = false, unique = true, length = 50)
    private String transactionCode;
    
    /**
     * 账户编号
     */
    @Column(name = "account_code", nullable = false, length = 50)
    private String accountCode;
    
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
    @Column(name = "security_code", nullable = false, length = 20)
    private String securityCode;
    
    /**
     * 证券名称
     */
    @Column(name = "security_name", length = 100)
    private String securityName;
    
    /**
     * 交易类型(1:买入,2:卖出,3:转入,4:转出,5:分红,6:送股,7:配股)
     */
    @Column(name = "transaction_type", nullable = false)
    private Integer transactionType;
    
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
     * 印花税
     */
    @Column(name = "tax", precision = 20, scale = 2)
    private BigDecimal tax;
    
    /**
     * 交易前数量
     */
    @Column(name = "before_quantity", precision = 20, scale = 8)
    private BigDecimal beforeQuantity;
    
    /**
     * 交易后数量
     */
    @Column(name = "after_quantity", precision = 20, scale = 8)
    private BigDecimal afterQuantity;
    
    /**
     * 交易前可用数量
     */
    @Column(name = "before_available_quantity", precision = 20, scale = 8)
    private BigDecimal beforeAvailableQuantity;
    
    /**
     * 交易后可用数量
     */
    @Column(name = "after_available_quantity", precision = 20, scale = 8)
    private BigDecimal afterAvailableQuantity;
    
    /**
     * 交易前成本价
     */
    @Column(name = "before_cost_price", precision = 20, scale = 8)
    private BigDecimal beforeCostPrice;
    
    /**
     * 交易后成本价
     */
    @Column(name = "after_cost_price", precision = 20, scale = 8)
    private BigDecimal afterCostPrice;
    
    /**
     * 交易时间
     */
    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;
    
    /**
     * 交易状态(1:待处理,2:已处理,3:已取消,4:失败)
     */
    @Column(name = "status", nullable = false)
    private Integer status;
    
    /**
     * 目标券商ID(转托管时使用)
     */
    @Column(name = "to_broker_id", length = 50)
    private String toBrokerId;
    
    /**
     * 目标券商名称(转托管时使用)
     */
    @Column(name = "to_broker_name", length = 100)
    private String toBrokerName;
    
    /**
     * 操作员ID
     */
    @Column(name = "operator_id", length = 50)
    private String operatorId;
    
    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;
    
    // ==================== 业务方法 ====================
    
    /**
     * 处理交易
     */
    public void processTransaction() {
        if (this.status == 2) {
            throw new IllegalStateException("交易已经处理");
        }
        this.status = 2; // 已处理状态
    }
    
    /**
     * 取消交易
     */
    public void cancelTransaction() {
        if (this.status == 2) {
            throw new IllegalStateException("已处理的交易无法取消");
        }
        this.status = 3; // 已取消状态
    }
    
    /**
     * 标记交易失败
     */
    public void failTransaction() {
        if (this.status == 2) {
            throw new IllegalStateException("已处理的交易无法标记为失败");
        }
        this.status = 4; // 失败状态
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