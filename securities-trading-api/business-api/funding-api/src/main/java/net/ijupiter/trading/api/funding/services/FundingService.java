package net.ijupiter.trading.api.funding.services;

import net.ijupiter.trading.api.funding.dtos.FundingAccountDTO;
import net.ijupiter.trading.api.funding.dtos.FundingTransferDTO;
import net.ijupiter.trading.api.funding.dtos.FundingTransactionDTO;
import net.ijupiter.trading.common.services.BaseService;

import java.util.List;
import java.util.Optional;

/**
 * 资金服务接口
 */
public interface FundingService extends BaseService<FundingAccountDTO, Long> {
    
    /**
     * 根据客户ID获取资金账户
     * @param customerId 客户ID
     * @return 资金账户列表
     */
    List<FundingAccountDTO> findByCustomerId(Long customerId);
    
    /**
     * 根据账户编号获取资金账户
     * @param accountCode 账户编号
     * @return 资金账户信息
     */
    Optional<FundingAccountDTO> findByAccountCode(String accountCode);
    
    /**
     * 获取资金转账记录
     * @param customerId 客户ID
     * @return 资金转账记录列表
     */
    List<FundingTransferDTO> findTransfersByCustomerId(Long customerId);
    
    /**
     * 获取资金流水记录
     * @param accountCode 账户编号
     * @return 资金流水记录列表
     */
    List<FundingTransactionDTO> findTransactionsByAccountCode(String accountCode);
    
    /**
     * 创建资金转账
     * @param transferDTO 转账信息
     * @return 转账记录
     */
    FundingTransferDTO createTransfer(FundingTransferDTO transferDTO);
    
    /**
     * 冻结资金账户
     * @param accountCode 账户编号
     * @param amount 冻结金额
     * @param reason 冻结原因
     * @param operatorId 操作员ID
     * @return 操作结果
     */
    boolean freezeAccount(String accountCode, java.math.BigDecimal amount, String reason, String operatorId);
    
    /**
     * 解冻资金账户
     * @param accountCode 账户编号
     * @param amount 解冻金额
     * @param operatorId 操作员ID
     * @return 操作结果
     */
    boolean unfreezeAccount(String accountCode, java.math.BigDecimal amount, String operatorId);
    
    /**
     * 获取资金统计信息
     * @return 统计信息
     */
    FundingStatistics getFundingStatistics();
    
    /**
     * 资金统计信息
     */
    class FundingStatistics {
        private long totalAccounts;
        private long activeAccounts;
        private long frozenAccounts;
        private java.math.BigDecimal totalBalance;
        private java.math.BigDecimal totalFrozenAmount;
        private long todayTransfers;
        private java.math.BigDecimal todayTransferAmount;
        
        public FundingStatistics(long totalAccounts, long activeAccounts, long frozenAccounts, 
                             java.math.BigDecimal totalBalance, java.math.BigDecimal totalFrozenAmount,
                             long todayTransfers, java.math.BigDecimal todayTransferAmount) {
            this.totalAccounts = totalAccounts;
            this.activeAccounts = activeAccounts;
            this.frozenAccounts = frozenAccounts;
            this.totalBalance = totalBalance;
            this.totalFrozenAmount = totalFrozenAmount;
            this.todayTransfers = todayTransfers;
            this.todayTransferAmount = todayTransferAmount;
        }
        
        public long getTotalAccounts() { return totalAccounts; }
        public long getActiveAccounts() { return activeAccounts; }
        public long getFrozenAccounts() { return frozenAccounts; }
        public java.math.BigDecimal getTotalBalance() { return totalBalance; }
        public java.math.BigDecimal getTotalFrozenAmount() { return totalFrozenAmount; }
        public long getTodayTransfers() { return todayTransfers; }
        public java.math.BigDecimal getTodayTransferAmount() { return todayTransferAmount; }
    }
}