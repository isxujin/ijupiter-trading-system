package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.entities.FundingAccountQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资金账户查询仓储接口
 */
@Repository
public interface FundingAccountQueryRepository extends JpaRepository<FundingAccountQueryEntity, Long> {
    
    /**
     * 根据客户ID查询资金账户
     */
    List<FundingAccountQueryEntity> findByCustomerId(Long customerId);
    
    /**
     * 根据客户ID和过滤条件查询资金账户
     */
    @Query("SELECT a FROM FundingAccountQueryEntity a WHERE " +
           "(:customerId IS NULL OR a.customerId = :customerId) AND " +
           "(:accountId IS NULL OR a.id = :accountId) AND " +
           "(:accountType IS NULL OR a.accountType = :accountType) AND " +
           "(:status IS NULL OR a.status = :status)")
    List<FundingAccountQueryEntity> findByCustomerIdAndFilters(
            @Param("customerId") Long customerId,
            @Param("accountId") Long accountId,
            @Param("accountType") String accountType,
            @Param("status") Integer status);
}