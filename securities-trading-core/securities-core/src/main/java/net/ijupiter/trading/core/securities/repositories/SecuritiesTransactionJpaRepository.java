package net.ijupiter.trading.core.securities.repositories;

import net.ijupiter.trading.core.securities.entities.SecuritiesTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 证券变动流水JPA数据访问接口
 */
@Repository
public interface SecuritiesTransactionJpaRepository extends JpaRepository<SecuritiesTransactionEntity, Long> {
    
    /**
     * 根据交易编号查询交易流水
     */
    SecuritiesTransactionEntity findByTransactionCode(String transactionCode);
    
    /**
     * 根据账户编号查询交易流水
     */
    List<SecuritiesTransactionEntity> findByAccountCode(String accountCode);
    
    /**
     * 根据客户ID查询交易流水
     */
    List<SecuritiesTransactionEntity> findByCustomerId(Long customerId);
    
    /**
     * 根据证券代码查询交易流水
     */
    List<SecuritiesTransactionEntity> findBySecurityCode(String securityCode);
    
    /**
     * 根据交易类型查询交易流水
     */
    List<SecuritiesTransactionEntity> findByTransactionType(Integer transactionType);
    
    /**
     * 根据交易状态查询交易流水
     */
    List<SecuritiesTransactionEntity> findByStatus(Integer status);
    
    /**
     * 根据账户编号和交易时间范围查询交易流水
     */
    @Query("SELECT t FROM SecuritiesTransactionEntity t WHERE t.accountCode = :accountCode AND t.transactionTime BETWEEN :startTime AND :endTime ORDER BY t.transactionTime DESC")
    List<SecuritiesTransactionEntity> findByAccountCodeAndTimeRange(@Param("accountCode") String accountCode, 
                                                                   @Param("startTime") LocalDateTime startTime, 
                                                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据客户ID和交易时间范围查询交易流水
     */
    @Query("SELECT t FROM SecuritiesTransactionEntity t WHERE t.customerId = :customerId AND t.transactionTime BETWEEN :startTime AND :endTime ORDER BY t.transactionTime DESC")
    List<SecuritiesTransactionEntity> findByCustomerIdAndTimeRange(@Param("customerId") Long customerId, 
                                                                 @Param("startTime") LocalDateTime startTime, 
                                                                 @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据证券代码和交易类型查询交易流水
     */
    @Query("SELECT t FROM SecuritiesTransactionEntity t WHERE t.securityCode = :securityCode AND t.transactionType = :transactionType ORDER BY t.transactionTime DESC")
    List<SecuritiesTransactionEntity> findBySecurityCodeAndTransactionType(@Param("securityCode") String securityCode, 
                                                                          @Param("transactionType") Integer transactionType);
    
    /**
     * 统计交易数量
     */
    @Query("SELECT COUNT(t) FROM SecuritiesTransactionEntity t")
    long countAll();
    
    /**
     * 根据交易类型统计交易数量
     */
    @Query("SELECT COUNT(t) FROM SecuritiesTransactionEntity t WHERE t.transactionType = :transactionType")
    long countByTransactionType(@Param("transactionType") Integer transactionType);
    
    /**
     * 根据交易状态统计交易数量
     */
    @Query("SELECT COUNT(t) FROM SecuritiesTransactionEntity t WHERE t.status = :status")
    long countByStatus(@Param("status") Integer status);
    
    /**
     * 统计总交易金额
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM SecuritiesTransactionEntity t WHERE t.status = 2")
    BigDecimal sumAllAmount();
    
    /**
     * 根据交易类型统计交易金额
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM SecuritiesTransactionEntity t WHERE t.transactionType = :transactionType AND t.status = 2")
    BigDecimal sumAmountByTransactionType(@Param("transactionType") Integer transactionType);
    
    /**
     * 根据账户编号统计交易金额
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM SecuritiesTransactionEntity t WHERE t.accountCode = :accountCode AND t.status = 2")
    BigDecimal sumAmountByAccountCode(@Param("accountCode") String accountCode);
}