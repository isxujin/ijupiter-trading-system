package net.ijupiter.trading.core.funding.entities;

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
 * 资金账户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "fund_funding_account")
public class FundingAccountEntity extends BaseEntity<FundingAccountEntity> {
    
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
     * 账户余额
     */
    @Column(name = "balance", precision = 20, scale = 2)
    private BigDecimal balance;
    
    /**
     * 冻结金额
     */
    @Column(name = "frozen_amount", precision = 20, scale = 2)
    private BigDecimal frozenAmount;
    
    /**
     * 可用余额
     */
    @Column(name = "available_balance", precision = 20, scale = 2)
    private BigDecimal availableBalance;
    
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
     * 备注
     */
    @Column(name = "remark", length = 500)
    private String remark;
    
    // ==================== 业务方法 ====================
    
    /**
     * 计算可用余额
     */
    public void calculateAvailableBalance() {
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        if (frozenAmount == null) {
            frozenAmount = BigDecimal.ZERO;
        }
        this.availableBalance = balance.subtract(frozenAmount);
    }
    
    /**
     * 冻结资金
     */
    public void freezeAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("冻结金额必须大于0");
        }
        if (amount.compareTo(availableBalance) > 0) {
            throw new IllegalArgumentException("冻结金额不能超过可用余额");
        }
        
        this.frozenAmount = this.frozenAmount.add(amount);
        calculateAvailableBalance();
    }
    
    /**
     * 解冻资金
     */
    public void unfreezeAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("解冻金额必须大于0");
        }
        if (amount.compareTo(frozenAmount) > 0) {
            throw new IllegalArgumentException("解冻金额不能超过冻结金额");
        }
        
        this.frozenAmount = this.frozenAmount.subtract(amount);
        calculateAvailableBalance();
    }
    
    /**
     * 增加余额
     */
    public void addBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("增加金额必须大于0");
        }
        
        this.balance = this.balance.add(amount);
        calculateAvailableBalance();
    }
    
    /**
     * 减少余额
     */
    public void reduceBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("减少金额必须大于0");
        }
        if (amount.compareTo(availableBalance) > 0) {
            throw new IllegalArgumentException("减少金额不能超过可用余额");
        }
        
        this.balance = this.balance.subtract(amount);
        calculateAvailableBalance();
    }
    

}