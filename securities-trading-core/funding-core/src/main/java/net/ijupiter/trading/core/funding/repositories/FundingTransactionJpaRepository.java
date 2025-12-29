package net.ijupiter.trading.core.funding.repositories;

import net.ijupiter.trading.core.funding.entities.FundingTransactionEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 资金流水JPA仓库
 */
@Repository
public interface FundingTransactionJpaRepository extends JpaRepository<FundingTransactionEntity, Long> {
    
    /**
     * 根据交易流水号查询
     */
    FundingTransactionEntity findByTransactionCode(String transactionCode);
    
    /**
     * 根据账户编号查询流水记录
     */
    List<FundingTransactionEntity> findByAccountCodeOrderByTransactionTimeDesc(String accountCode);
    
    /**
     * 根据客户编号查询流水记录
     */
    List<FundingTransactionEntity> findByCustomerCodeOrderByTransactionTimeDesc(String customerCode);
    
    /**
     * 根据账户编号和时间范围查询流水记录
     */
    List<FundingTransactionEntity> findByAccountCodeAndTransactionTimeBetweenOrderByTransactionTimeDesc(
        String accountCode, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 根据交易类型查询流水记录
     */
    List<FundingTransactionEntity> findByTransactionTypeOrderByTransactionTimeDesc(Integer transactionType);
    
    /**
     * 根据账户编号和交易类型查询流水记录
     */
    List<FundingTransactionEntity> findByAccountCodeAndTransactionTypeOrderByTransactionTimeDesc(
        String accountCode, Integer transactionType);
    
    /**
     * 根据关联业务编号查询流水记录
     */
    List<FundingTransactionEntity> findByRelatedBusinessCodeOrderByTransactionTimeDesc(String relatedBusinessCode);
    
    /**
     * 统计指定账户在指定时间范围内的总交易金额
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM FundingTransactionEntity t " +
           "WHERE t.accountCode = :accountCode " +
           "AND t.transactionTime BETWEEN :startTime AND :endTime")
    BigDecimal sumAmountByAccountAndTimeRange(
        @Param("accountCode") String accountCode,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计指定账户在指定时间范围内指定交易类型的总金额
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM FundingTransactionEntity t " +
           "WHERE t.accountCode = :accountCode " +
           "AND t.transactionType = :transactionType " +
           "AND t.transactionTime BETWEEN :startTime AND :endTime")
    BigDecimal sumAmountByAccountAndTypeAndTimeRange(
        @Param("accountCode") String accountCode,
        @Param("transactionType") Integer transactionType,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计指定账户在指定时间范围内的交易笔数
     */
    @Query("SELECT COUNT(t) FROM FundingTransactionEntity t " +
           "WHERE t.accountCode = :accountCode " +
           "AND t.transactionTime BETWEEN :startTime AND :endTime")
    Long countTransactionsByAccountAndTimeRange(
        @Param("accountCode") String accountCode,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime);
    
    /**
     * 查询指定账户的最新N条流水记录
     */
    default List<FundingTransactionEntity> findTopNByAccountCodeOrderByTransactionTimeDesc(String accountCode, int n) {
        Pageable pageable = PageRequest.of(
                0,
                n,
                Sort.by(Sort.Direction.DESC, "transactionTime") // 字段名与实体类一致
        );
        return findByAccountCode(accountCode, pageable);
    }

    /**
     * 分页查询指定账户的流水记录
     */
    List<FundingTransactionEntity> findByAccountCode(String accountCode, Pageable pageable);

    /**
     * 根据操作员ID查询流水记录
     */
    List<FundingTransactionEntity> findByOperatorIdOrderByTransactionTimeDesc(String operatorId);
    
    /**
     * 根据金额范围查询流水记录
     */
    @Query("SELECT t FROM FundingTransactionEntity t " +
           "WHERE ABS(t.amount) BETWEEN :minAmount AND :maxAmount " +
           "ORDER BY t.transactionTime DESC")
    List<FundingTransactionEntity> findByAmountRangeOrderByTransactionTimeDesc(
        @Param("minAmount") BigDecimal minAmount,
        @Param("maxAmount") BigDecimal maxAmount);
}