package net.ijupiter.trading.core.funding.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金流水实体
 * 记录所有资金相关的交易流水
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "fund_funding_transaction")
public class FundingTransactionEntity extends BaseEntity<FundingTransactionEntity> {

    /**
     * 交易流水号
     */
    @Column(name = "transaction_code", nullable = false, unique = true, length = 50)
    private String transactionCode;

    /**
     * 账户编号
     */
    @Column(name = "account_code", length = 50)
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
     * 交易类型
     */
    @Column(name = "transaction_type", nullable = false)
    private Integer transactionType;

    /**
     * 交易金额
     * 正数表示增加，负数表示减少
     */
    @Column(name = "amount", precision = 20, scale = 2)
    private BigDecimal amount;

    /**
     * 交易前余额
     */
    @Column(name = "balance_before", precision = 20, scale = 2)
    private BigDecimal balanceBefore;

    /**
     * 交易后余额
     */
    @Column(name = "balance_after", precision = 20, scale = 2)
    private BigDecimal balanceAfter;

    /**
     * 交易时间
     */
    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    /**
     * 关联业务编号
     * 如转账编号、订单编号等
     */
    @Column(name = "related_business_code", length = 50)
    private String relatedBusinessCode;

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

    /**
     * 构造函数
     */
    public FundingTransactionEntity(String transactionCode, String accountCode,
                                 Integer transactionType, BigDecimal amount) {
        this.transactionCode = transactionCode;
        this.accountCode = accountCode;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    // ==================== 业务方法 ====================

    /**
     * 计算并设置交易后余额
     * @param transactionAmount 交易金额
     */
    public void calculateBalanceAfter(BigDecimal transactionAmount) {
        if (balanceBefore == null) {
            balanceBefore = BigDecimal.ZERO;
        }
        this.balanceAfter = balanceBefore.add(transactionAmount);
    }

    /**
     * 验证交易金额是否有效
     */
    public void validateAmount() {
        if (amount == null) {
            throw new IllegalArgumentException("交易金额不能为空");
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("交易金额不能为零");
        }
    }

    /**
     * 验证必要字段是否为空
     */
    public void validateRequiredFields() {
        if (transactionCode == null || transactionCode.trim().isEmpty()) {
            throw new IllegalArgumentException("交易流水号不能为空");
        }
        if (accountCode == null || accountCode.trim().isEmpty()) {
            throw new IllegalArgumentException("账户编号不能为空");
        }
        if (transactionType == null) {
            throw new IllegalArgumentException("交易类型不能为空");
        }
        if (transactionTime == null) {
            this.transactionTime = LocalDateTime.now();
        }
    }

    // ==================== 资金交易类型常量 ====================

    /**
     * 存款
     */
    public static final int TRANSACTION_TYPE_DEPOSIT = 1;

    /**
     * 取款
     */
    public static final int TRANSACTION_TYPE_WITHDRAW = 2;

    /**
     * 转入
     */
    public static final int TRANSACTION_TYPE_TRANSFER_IN = 3;

    /**
     * 转出
     */
    public static final int TRANSACTION_TYPE_TRANSFER_OUT = 4;

    /**
     * 冻结
     */
    public static final int TRANSACTION_TYPE_FREEZE = 5;

    /**
     * 解冻
     */
    public static final int TRANSACTION_TYPE_UNFREEZE = 6;

    /**
     * 利息
     */
    public static final int TRANSACTION_TYPE_INTEREST = 7;

    /**
     * 手续费
     */
    public static final int TRANSACTION_TYPE_FEE = 8;

    /**
     * 退款
     */
    public static final int TRANSACTION_TYPE_REFUND = 9;

    // ==================== 业务方法 ====================

    /**
     * 创建存款交易记录
     */
    public static FundingTransactionEntity createDeposit(String transactionCode, String accountCode,
                                                     Long customerId, String customerCode,
                                                     BigDecimal amount, BigDecimal balanceBefore,
                                                     String operatorId, String remark) {
        FundingTransactionEntity transaction = new FundingTransactionEntity(
            transactionCode, accountCode, TRANSACTION_TYPE_DEPOSIT, amount);
        transaction.setCustomerId(customerId);
        transaction.setCustomerCode(customerCode);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setOperatorId(operatorId);
        transaction.setRemark(remark);
        transaction.setTransactionTime(java.time.LocalDateTime.now());
        transaction.calculateBalanceAfter(amount);
        return transaction;
    }

    /**
     * 创建取款交易记录
     */
    public static FundingTransactionEntity createWithdraw(String transactionCode, String accountCode,
                                                      Long customerId, String customerCode,
                                                      BigDecimal amount, BigDecimal balanceBefore,
                                                      String operatorId, String remark) {
        FundingTransactionEntity transaction = new FundingTransactionEntity(
            transactionCode, accountCode, TRANSACTION_TYPE_WITHDRAW, amount.negate());
        transaction.setCustomerId(customerId);
        transaction.setCustomerCode(customerCode);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setOperatorId(operatorId);
        transaction.setRemark(remark);
        transaction.setTransactionTime(java.time.LocalDateTime.now());
        transaction.calculateBalanceAfter(amount.negate());
        return transaction;
    }

    /**
     * 创建转入交易记录
     */
    public static FundingTransactionEntity createTransferIn(String transactionCode, String accountCode,
                                                        Long customerId, String customerCode,
                                                        BigDecimal amount, BigDecimal balanceBefore,
                                                        String relatedTransferCode, String operatorId, String remark) {
        FundingTransactionEntity transaction = new FundingTransactionEntity(
            transactionCode, accountCode, TRANSACTION_TYPE_TRANSFER_IN, amount);
        transaction.setCustomerId(customerId);
        transaction.setCustomerCode(customerCode);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setRelatedBusinessCode(relatedTransferCode);
        transaction.setOperatorId(operatorId);
        transaction.setRemark(remark);
        transaction.setTransactionTime(java.time.LocalDateTime.now());
        transaction.calculateBalanceAfter(amount);
        return transaction;
    }

    /**
     * 创建转出交易记录
     */
    public static FundingTransactionEntity createTransferOut(String transactionCode, String accountCode,
                                                         Long customerId, String customerCode,
                                                         BigDecimal amount, BigDecimal balanceBefore,
                                                         String relatedTransferCode, String operatorId, String remark) {
        FundingTransactionEntity transaction = new FundingTransactionEntity(
            transactionCode, accountCode, TRANSACTION_TYPE_TRANSFER_OUT, amount.negate());
        transaction.setCustomerId(customerId);
        transaction.setCustomerCode(customerCode);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setRelatedBusinessCode(relatedTransferCode);
        transaction.setOperatorId(operatorId);
        transaction.setRemark(remark);
        transaction.setTransactionTime(java.time.LocalDateTime.now());
        transaction.calculateBalanceAfter(amount.negate());
        return transaction;
    }

    /**
     * 创建冻结交易记录
     */
    public static FundingTransactionEntity createFreeze(String transactionCode, String accountCode,
                                                     Long customerId, String customerCode,
                                                     BigDecimal amount, BigDecimal balanceBefore,
                                                     String operatorId, String remark) {
        FundingTransactionEntity transaction = new FundingTransactionEntity(
            transactionCode, accountCode, TRANSACTION_TYPE_FREEZE, amount.negate());
        transaction.setCustomerId(customerId);
        transaction.setCustomerCode(customerCode);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setOperatorId(operatorId);
        transaction.setRemark(remark);
        transaction.setTransactionTime(java.time.LocalDateTime.now());
        transaction.calculateBalanceAfter(amount.negate());
        return transaction;
    }

    /**
     * 创建解冻交易记录
     */
    public static FundingTransactionEntity createUnfreeze(String transactionCode, String accountCode,
                                                       Long customerId, String customerCode,
                                                       BigDecimal amount, BigDecimal balanceBefore,
                                                       String operatorId, String remark) {
        FundingTransactionEntity transaction = new FundingTransactionEntity(
            transactionCode, accountCode, TRANSACTION_TYPE_UNFREEZE, amount);
        transaction.setCustomerId(customerId);
        transaction.setCustomerCode(customerCode);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setOperatorId(operatorId);
        transaction.setRemark(remark);
        transaction.setTransactionTime(java.time.LocalDateTime.now());
        transaction.calculateBalanceAfter(amount);
        return transaction;
    }
}