package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.entities.TransactionQueryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 交易流水查询仓储接口
 */
@Repository
public interface TransactionQueryRepository extends JpaRepository<TransactionQueryEntity, Long> {
    
    /**
     * 根据客户ID查询交易流水
     */
    Page<TransactionQueryEntity> findByCustomerId(Long customerId, Pageable pageable);
    
    /**
     * 根据条件查询客户交易流水
     */
    @Query("SELECT t FROM TransactionQueryEntity t WHERE " +
           "(:customerId IS NULL OR t.customerId = :customerId) AND " +
           "(:securityCode IS NULL OR t.securityCode = :securityCode) AND " +
           "(:transactionType IS NULL OR t.transactionType = :transactionType) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:startTime IS NULL OR t.transactionTime >= :startTime) AND " +
           "(:endTime IS NULL OR t.transactionTime <= :endTime)")
    Page<TransactionQueryEntity> findCustomerTransactionsWithFilters(
            @Param("customerId") Long customerId,
            @Param("securityCode") String securityCode,
            @Param("transactionType") Integer transactionType,
            @Param("status") Integer status,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable);
}