package net.ijupiter.trading.core.settlement.entities;

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
 * 清算实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "sett_settlement")
public class SettlementEntity extends BaseEntity<SettlementEntity> {
    
    /**
     * 清算编号
     */
    @Column(name = "settlement_code", nullable = false, unique = true, length = 50)
    private String settlementCode;
    
    /**
     * 清算类型(1:资金清算,2:证券清算,3:衍生品清算)
     */
    @Column(name = "settlement_type", nullable = false)
    private Integer settlementType;
    
    /**
     * 交易编号
     */
    @Column(name = "trade_code", length = 50)
    private String tradeCode;
    
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
     * 清算数量
     */
    @Column(name = "quantity", precision = 20, scale = 8)
    private BigDecimal quantity;
    
    /**
     * 清算价格
     */
    @Column(name = "price", precision = 20, scale = 8)
    private BigDecimal price;
    
    /**
     * 清算金额
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
     * 清算状态(1:待清算,2:清算中,3:已清算,4:清算失败)
     */
    @Column(name = "status", nullable = false)
    private Integer status;
    
    /**
     * 清算日期
     */
    @Column(name = "settlement_date")
    private LocalDateTime settlementDate;
    
    /**
     * 清算确认日期
     */
    @Column(name = "confirm_date")
    private LocalDateTime confirmDate;
    
    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;
    
    // ==================== 业务方法 ====================
    
    /**
     * 处理清算
     */
    public void processSettlement() {
        if (this.status == 2) {
            throw new IllegalStateException("清算正在处理中");
        }
        if (this.status == 3) {
            throw new IllegalStateException("清算已经完成");
        }
        if (this.status == 4) {
            throw new IllegalStateException("清算已失败，无法重新处理");
        }
        this.status = 2; // 清算中状态
    }
    
    /**
     * 完成清算
     */
    public void completeSettlement() {
        if (this.status != 2) {
            throw new IllegalStateException("只有处理中的清算才能完成");
        }
        this.status = 3; // 已清算状态
        this.confirmDate = LocalDateTime.now();
    }
    
    /**
     * 标记清算失败
     */
    public void failSettlement() {
        if (this.status != 2) {
            throw new IllegalStateException("只有处理中的清算才能标记为失败");
        }
        this.status = 4; // 清算失败状态
    }
    
    /**
     * 计算清算金额
     */
    public void calculateAmount() {
        if (this.price != null && this.quantity != null) {
            this.amount = this.price.multiply(this.quantity);
        }
    }

}