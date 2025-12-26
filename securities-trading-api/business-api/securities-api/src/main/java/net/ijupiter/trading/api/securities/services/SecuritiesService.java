package net.ijupiter.trading.api.securities.services;

import net.ijupiter.trading.api.securities.dtos.SecuritiesAccountDTO;
import net.ijupiter.trading.api.securities.dtos.SecuritiesPositionDTO;
import net.ijupiter.trading.api.securities.dtos.SecuritiesTransferDTO;
import net.ijupiter.trading.common.services.BaseService;

import java.util.List;
import java.util.Optional;

/**
 * 证券服务接口
 */
public interface SecuritiesService extends BaseService<SecuritiesAccountDTO, Long> {
    
    /**
     * 根据客户ID获取证券账户
     * @param customerId 客户ID
     * @return 证券账户列表
     */
    List<SecuritiesAccountDTO> findByCustomerId(Long customerId);
    
    /**
     * 根据账户编号获取证券账户
     * @param accountCode 账户编号
     * @return 证券账户信息
     */
    Optional<SecuritiesAccountDTO> findByAccountCode(String accountCode);
    
    /**
     * 获取证券持仓列表
     * @param customerId 客户ID
     * @return 证券持仓列表
     */
    List<SecuritiesPositionDTO> findPositionsByCustomerId(Long customerId);
    
    /**
     * 根据账户编号获取证券持仓
     * @param accountCode 账户编号
     * @return 证券持仓列表
     */
    List<SecuritiesPositionDTO> findPositionsByAccountCode(String accountCode);
    
    /**
     * 获取证券转托管记录
     * @param customerId 客户ID
     * @return 证券转托管记录列表
     */
    List<SecuritiesTransferDTO> findTransfersByCustomerId(Long customerId);
    
    /**
     * 创建证券转托管
     * @param transferDTO 转托管信息
     * @return 转托管记录
     */
    SecuritiesTransferDTO createTransfer(SecuritiesTransferDTO transferDTO);
    
    /**
     * 获取证券统计信息
     * @return 统计信息
     */
    SecuritiesStatistics getSecuritiesStatistics();
    
    /**
     * 证券统计信息
     */
    class SecuritiesStatistics {
        private long totalAccounts;
        private long activeAccounts;
        private long frozenAccounts;
        private java.math.BigDecimal totalMarketValue;
        private java.math.BigDecimal totalAssets;
        private long totalPositions;
        private long todayTransfers;
        
        public SecuritiesStatistics(long totalAccounts, long activeAccounts, long frozenAccounts, 
                               java.math.BigDecimal totalMarketValue, java.math.BigDecimal totalAssets,
                               long totalPositions, long todayTransfers) {
            this.totalAccounts = totalAccounts;
            this.activeAccounts = activeAccounts;
            this.frozenAccounts = frozenAccounts;
            this.totalMarketValue = totalMarketValue;
            this.totalAssets = totalAssets;
            this.totalPositions = totalPositions;
            this.todayTransfers = todayTransfers;
        }
        
        public long getTotalAccounts() { return totalAccounts; }
        public long getActiveAccounts() { return activeAccounts; }
        public long getFrozenAccounts() { return frozenAccounts; }
        public java.math.BigDecimal getTotalMarketValue() { return totalMarketValue; }
        public java.math.BigDecimal getTotalAssets() { return totalAssets; }
        public long getTotalPositions() { return totalPositions; }
        public long getTodayTransfers() { return todayTransfers; }
    }
}