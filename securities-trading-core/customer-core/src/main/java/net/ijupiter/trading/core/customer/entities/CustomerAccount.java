package net.ijupiter.trading.core.customer.entities;

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
 * 客户账户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CustomerAccount extends BaseEntity<CustomerAccount> {
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 客户编号
     */
    private String customerCode;
    
    /**
     * 账户编号
     */
    private String accountCode;
    
    /**
     * 账户类型(1:资金账户,2:证券账户)
     */
    private Integer accountType;
    
    /**
     * 账户名称
     */
    private String accountName;
    
    /**
     * 账户余额
     */
    private BigDecimal balance;
    
    /**
     * 冻结金额
     */
    private BigDecimal frozenAmount;
    
    /**
     * 可用余额
     */
    private BigDecimal availableBalance;
    
    /**
     * 账户状态(1:正常,2:冻结,3:注销)
     */
    private Integer status;
    
    /**
     * 开户日期
     */
    private LocalDateTime openDate;
    
    /**
     * 备注
     */
    private String remark;
    
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
        setUpdateTime(LocalDateTime.now());
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
        setUpdateTime(LocalDateTime.now());
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
        setUpdateTime(LocalDateTime.now());
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
        setUpdateTime(LocalDateTime.now());
    }
}