package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.entities.DailySecuritiesProfitQueryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * 每日证券收益查询仓储接口
 */
@Repository
public interface DailySecuritiesProfitQueryRepository extends JpaRepository<DailySecuritiesProfitQueryEntity, Long> {
    
    /**
     * 根据条件查询客户每日证券收益
     */
    @Query("SELECT r FROM DailySecuritiesProfitQueryEntity r WHERE " +
           "(:customerId IS NULL OR r.customerId = :customerId) AND " +
           "(:securityCode IS NULL OR r.securityCode = :securityCode) AND " +
           "(:startDate IS NULL OR r.tradeDate >= :startDate) AND " +
           "(:endDate IS NULL OR r.tradeDate <= :endDate) " +
           "ORDER BY r.tradeDate DESC")
    Page<DailySecuritiesProfitQueryEntity> findCustomerDailySecuritiesProfitWithFilters(
            @Param("customerId") Long customerId,
            @Param("securityCode") String securityCode,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
}