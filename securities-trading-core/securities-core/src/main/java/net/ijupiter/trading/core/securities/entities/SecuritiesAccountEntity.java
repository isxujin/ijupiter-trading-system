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

import java.time.LocalDateTime;

/**
 * 证券账户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "secu_securities_account")
public class SecuritiesAccountEntity extends BaseEntity<SecuritiesAccountEntity> {
    
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
     * 账户编号
     */
    @Column(name = "account_code", nullable = false, unique = true, length = 50)
    private String accountCode;
    
    /**
     * 账户名称
     */
    @Column(name = "account_name", length = 100)
    private String accountName;
    
    /**
     * 账户类型(1:A股,2:港股,3:美股,4:债券,5:基金)
     */
    @Column(name = "account_type")
    private Integer accountType;
    
    /**
     * 账户状态(1:正常,2:冻结,3:注销)
     */
    @Column(name = "status", nullable = false)
    private Integer status;
    
    /**
     * 开户日期
     */
    @Column(name = "open_date")
    private LocalDateTime openDate;
    
    /**
     * 冻结原因
     */
    @Column(name = "freeze_reason", length = 200)
    private String freezeReason;
    
    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;
    
    // ==================== 业务方法 ====================
    
    /**
     * 冻结账户
     */
    public void freezeAccount(String reason) {
        if (this.status == 2) {
            throw new IllegalArgumentException("账户已经是冻结状态");
        }
        this.status = 2; // 冻结状态
        this.freezeReason = reason;
    }
    
    /**
     * 解冻账户
     */
    public void unfreezeAccount() {
        if (this.status != 2) {
            throw new IllegalArgumentException("账户未处于冻结状态");
        }
        this.status = 1; // 正常状态
        this.freezeReason = null;
    }
    
    /**
     * 注销账户
     */
    public void closeAccount() {
        if (this.status == 3) {
            throw new IllegalArgumentException("账户已经是注销状态");
        }
        this.status = 3; // 注销状态
    }
    

}