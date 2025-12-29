package net.ijupiter.trading.core.funding.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 资金账户台账实体
 * 用于登记资金余额、发生额账簿
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "fund_funding_account_ledger",
       indexes = {@Index(name = "idx_account_date", columnList = "account_code, ledger_date")},
       uniqueConstraints = {@UniqueConstraint(columnNames = {"account_code", "ledger_date"})})
public class FundingAccountLedgerEntity extends BaseEntity<FundingAccountLedgerEntity> {
    
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
     * 账户类型(1:普通账户,2:保证金账户,3:特殊账户)
     */
    @Column(name = "account_type", nullable = false)
    private Integer accountType;
    
    /**
     * 台账日期
     */
    @Column(name = "ledger_date", nullable = false)
    private LocalDate ledgerDate;
    
    /**
     * 期初余额
     */
    @Column(name = "opening_balance", precision = 20, scale = 2)
    private BigDecimal openingBalance;
    
    /**
     * 期末余额
     */
    @Column(name = "closing_balance", precision = 20, scale = 2)
    private BigDecimal closingBalance;
    
    /**
     * 当日存款金额
     */
    @Column(name = "deposit_amount", precision = 20, scale = 2)
    private BigDecimal depositAmount;
    
    /**
     * 当日取款金额
     */
    @Column(name = "withdraw_amount", precision = 20, scale = 2)
    private BigDecimal withdrawAmount;
    
    /**
     * 当日转入金额
     */
    @Column(name = "transfer_in_amount", precision = 20, scale = 2)
    private BigDecimal transferInAmount;
    
    /**
     * 当日转出金额
     */
    @Column(name = "transfer_out_amount", precision = 20, scale = 2)
    private BigDecimal transferOutAmount;
    
    /**
     * 当日冻结金额
     */
    @Column(name = "freeze_amount", precision = 20, scale = 2)
    private BigDecimal freezeAmount;
    
    /**
     * 当日解冻金额
     */
    @Column(name = "unfreeze_amount", precision = 20, scale = 2)
    private BigDecimal unfreezeAmount;
    
    /**
     * 当日利息金额
     */
    @Column(name = "interest_amount", precision = 20, scale = 2)
    private BigDecimal interestAmount;
    
    /**
     * 当日手续费金额
     */
    @Column(name = "fee_amount", precision = 20, scale = 2)
    private BigDecimal feeAmount;
    
    /**
     * 当日退款金额
     */
    @Column(name = "refund_amount", precision = 20, scale = 2)
    private BigDecimal refundAmount;
    
    /**
     * 当日总发生额
     */
    @Column(name = "total_transaction_amount", precision = 20, scale = 2)
    private BigDecimal totalTransactionAmount;
    
    /**
     * 当日交易笔数
     */
    @Column(name = "transaction_count")
    private Integer transactionCount;
    
    /**
     * 冻结余额
     */
    @Column(name = "frozen_balance", precision = 20, scale = 2)
    private BigDecimal frozenBalance;
    
    /**
     * 可用余额
     */
    @Column(name = "available_balance", precision = 20, scale = 2)
    private BigDecimal availableBalance;
    
    /**
     * 状态(1:正常,2:冻结,3:注销)
     */
    @Column(name = "status", nullable = false)
    private Integer status;
    
    /**
     * 最后更新时间
     */
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;
    
    // ==================== 账户类型常量 ====================
    
    /**
     * 普通账户
     */
    public static final int ACCOUNT_TYPE_NORMAL = 1;
    
    /**
     * 保证金账户
     */
    public static final int ACCOUNT_TYPE_MARGIN = 2;
    
    /**
     * 特殊账户
     */
    public static final int ACCOUNT_TYPE_SPECIAL = 3;
    
    // ==================== 状态常量 ====================
    
    /**
     * 正常
     */
    public static final int STATUS_NORMAL = 1;
    
    /**
     * 冻结
     */
    public static final int STATUS_FROZEN = 2;
    
    /**
     * 注销
     */
    public static final int STATUS_CLOSED = 3;
    
    // ==================== 业务方法 ====================
    
    /**
     * 初始化新台账记录
     */
    public static FundingAccountLedgerEntity createNew(String accountCode, Long customerId, 
                                                    String customerCode, Integer accountType, 
                                                    LocalDate ledgerDate, BigDecimal openingBalance,
                                                    BigDecimal frozenBalance) {
        FundingAccountLedgerEntity ledger = new FundingAccountLedgerEntity();
        ledger.setAccountCode(accountCode);
        ledger.setCustomerId(customerId);
        ledger.setCustomerCode(customerCode);
        ledger.setAccountType(accountType);
        ledger.setLedgerDate(ledgerDate);
        ledger.setOpeningBalance(openingBalance != null ? openingBalance : BigDecimal.ZERO);
        ledger.setClosingBalance(openingBalance != null ? openingBalance : BigDecimal.ZERO);
        ledger.setDepositAmount(BigDecimal.ZERO);
        ledger.setWithdrawAmount(BigDecimal.ZERO);
        ledger.setTransferInAmount(BigDecimal.ZERO);
        ledger.setTransferOutAmount(BigDecimal.ZERO);
        ledger.setFreezeAmount(BigDecimal.ZERO);
        ledger.setUnfreezeAmount(BigDecimal.ZERO);
        ledger.setInterestAmount(BigDecimal.ZERO);
        ledger.setFeeAmount(BigDecimal.ZERO);
        ledger.setRefundAmount(BigDecimal.ZERO);
        ledger.setTotalTransactionAmount(BigDecimal.ZERO);
        ledger.setTransactionCount(0);
        ledger.setFrozenBalance(frozenBalance != null ? frozenBalance : BigDecimal.ZERO);
        ledger.setAvailableBalance(ledger.getClosingBalance().subtract(
            frozenBalance != null ? frozenBalance : BigDecimal.ZERO));
        ledger.setStatus(STATUS_NORMAL);
        ledger.setLastUpdateTime(LocalDateTime.now());
        return ledger;
    }
    
    /**
     * 从前一天台账创建当天台账
     */
    public static FundingAccountLedgerEntity createFromPrevious(FundingAccountLedgerEntity previousLedger, LocalDate currentDate) {
        FundingAccountLedgerEntity newLedger = new FundingAccountLedgerEntity();
        newLedger.setAccountCode(previousLedger.getAccountCode());
        newLedger.setCustomerId(previousLedger.getCustomerId());
        newLedger.setCustomerCode(previousLedger.getCustomerCode());
        newLedger.setAccountType(previousLedger.getAccountType());
        newLedger.setLedgerDate(currentDate);
        newLedger.setOpeningBalance(previousLedger.getClosingBalance());
        newLedger.setClosingBalance(previousLedger.getClosingBalance());
        newLedger.setDepositAmount(BigDecimal.ZERO);
        newLedger.setWithdrawAmount(BigDecimal.ZERO);
        newLedger.setTransferInAmount(BigDecimal.ZERO);
        newLedger.setTransferOutAmount(BigDecimal.ZERO);
        newLedger.setFreezeAmount(BigDecimal.ZERO);
        newLedger.setUnfreezeAmount(BigDecimal.ZERO);
        newLedger.setInterestAmount(BigDecimal.ZERO);
        newLedger.setFeeAmount(BigDecimal.ZERO);
        newLedger.setRefundAmount(BigDecimal.ZERO);
        newLedger.setTotalTransactionAmount(BigDecimal.ZERO);
        newLedger.setTransactionCount(0);
        newLedger.setFrozenBalance(previousLedger.getFrozenBalance());
        newLedger.setAvailableBalance(previousLedger.getAvailableBalance());
        newLedger.setStatus(previousLedger.getStatus());
        newLedger.setLastUpdateTime(LocalDateTime.now());
        return newLedger;
    }
    
    /**
     * 添加存款交易
     */
    public void addDeposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("存款金额必须大于0");
        }
        this.depositAmount = this.depositAmount.add(amount);
        this.closingBalance = this.closingBalance.add(amount);
        this.availableBalance = this.availableBalance.add(amount);
        this.totalTransactionAmount = this.totalTransactionAmount.add(amount);
        this.transactionCount = this.transactionCount + 1;
        this.lastUpdateTime = LocalDateTime.now();
    }
    
    /**
     * 添加取款交易
     */
    public void addWithdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("取款金额必须大于0");
        }
        this.withdrawAmount = this.withdrawAmount.add(amount);
        this.closingBalance = this.closingBalance.subtract(amount);
        this.availableBalance = this.availableBalance.subtract(amount);
        this.totalTransactionAmount = this.totalTransactionAmount.add(amount);
        this.transactionCount = this.transactionCount + 1;
        this.lastUpdateTime = LocalDateTime.now();
    }
    
    /**
     * 添加转入交易
     */
    public void addTransferIn(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("转入金额必须大于0");
        }
        this.transferInAmount = this.transferInAmount.add(amount);
        this.closingBalance = this.closingBalance.add(amount);
        this.availableBalance = this.availableBalance.add(amount);
        this.totalTransactionAmount = this.totalTransactionAmount.add(amount);
        this.transactionCount = this.transactionCount + 1;
        this.lastUpdateTime = LocalDateTime.now();
    }
    
    /**
     * 添加转出交易
     */
    public void addTransferOut(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("转出金额必须大于0");
        }
        this.transferOutAmount = this.transferOutAmount.add(amount);
        this.closingBalance = this.closingBalance.subtract(amount);
        this.availableBalance = this.availableBalance.subtract(amount);
        this.totalTransactionAmount = this.totalTransactionAmount.add(amount);
        this.transactionCount = this.transactionCount + 1;
        this.lastUpdateTime = LocalDateTime.now();
    }
    
    /**
     * 添加冻结操作
     */
    public void addFreeze(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("冻结金额必须大于0");
        }
        this.freezeAmount = this.freezeAmount.add(amount);
        this.frozenBalance = this.frozenBalance.add(amount);
        this.availableBalance = this.availableBalance.subtract(amount);
        this.totalTransactionAmount = this.totalTransactionAmount.add(amount);
        this.transactionCount = this.transactionCount + 1;
        this.lastUpdateTime = LocalDateTime.now();
    }
    
    /**
     * 添加解冻操作
     */
    public void addUnfreeze(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("解冻金额必须大于0");
        }
        this.unfreezeAmount = this.unfreezeAmount.add(amount);
        this.frozenBalance = this.frozenBalance.subtract(amount);
        this.availableBalance = this.availableBalance.add(amount);
        this.totalTransactionAmount = this.totalTransactionAmount.add(amount);
        this.transactionCount = this.transactionCount + 1;
        this.lastUpdateTime = LocalDateTime.now();
    }
    
    /**
     * 添加利息
     */
    public void addInterest(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("利息金额不能为0");
        }
        this.interestAmount = this.interestAmount.add(amount);
        this.closingBalance = this.closingBalance.add(amount);
        this.availableBalance = this.availableBalance.add(amount);
        this.totalTransactionAmount = this.totalTransactionAmount.add(
            amount.abs());
        this.transactionCount = this.transactionCount + 1;
        this.lastUpdateTime = LocalDateTime.now();
    }
    
    /**
     * 添加手续费
     */
    public void addFee(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("手续费金额必须大于0");
        }
        this.feeAmount = this.feeAmount.add(amount);
        this.closingBalance = this.closingBalance.subtract(amount);
        this.availableBalance = this.availableBalance.subtract(amount);
        this.totalTransactionAmount = this.totalTransactionAmount.add(amount);
        this.transactionCount = this.transactionCount + 1;
        this.lastUpdateTime = LocalDateTime.now();
    }
    
    /**
     * 添加退款
     */
    public void addRefund(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("退款金额必须大于0");
        }
        this.refundAmount = this.refundAmount.add(amount);
        this.closingBalance = this.closingBalance.add(amount);
        this.availableBalance = this.availableBalance.add(amount);
        this.totalTransactionAmount = this.totalTransactionAmount.add(amount);
        this.transactionCount = this.transactionCount + 1;
        this.lastUpdateTime = LocalDateTime.now();
    }
    
    /**
     * 重新计算可用余额
     */
    public void recalculateAvailableBalance() {
        this.availableBalance = this.closingBalance.subtract(this.frozenBalance);
    }
}