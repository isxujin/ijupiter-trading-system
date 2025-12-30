package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.entities.FundingTransactionQueryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 资金流水查询仓储接口
 */
@Repository
public interface FundingTransactionQueryRepository extends JpaRepository<FundingTransactionQueryEntity, Long> {
    
    /**
     * 根据客户ID查询资金流水
     */
    Page<FundingTransactionQueryEntity> findByCustomerId(Long customerId, Pageable pageable);
    
    /**
     * 根据条件查询客户资金流水
     */
    @Query("SELECT t FROM FundingTransactionQueryEntity t WHERE " +
           "(:customerId IS NULL OR t.customerId = :customerId) AND " +
           "(:fundingAccountId IS NULL OR t.fundingAccountId = :fundingAccountId) AND " +
           "(:transactionType IS NULL OR t.transactionType = :transactionType) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:startTime IS NULL OR t.transactionTime >= :startTime) AND " +
           "(:endTime IS NULL OR t.transactionTime <= :endTime)")
    Page<FundingTransactionQueryEntity> findCustomerFundingTransactionsWithFilters(
            @Param("customerId") Long customerId,
            @Param("fundingAccountId") Long fundingAccountId,
            @Param("transactionType") Integer transactionType,
            @Param("status") Integer status,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable);
}