package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.entities.SecuritiesPositionQueryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 证券持仓查询仓储接口
 */
@Repository
public interface SecuritiesPositionQueryRepository extends JpaRepository<SecuritiesPositionQueryEntity, Long> {
    
    /**
     * 根据客户ID查询证券持仓
     */
    Page<SecuritiesPositionQueryEntity> findByCustomerId(Long customerId, Pageable pageable);
    
    /**
     * 根据客户ID查询证券持仓（不分页）
     */
    List<SecuritiesPositionQueryEntity> findByCustomerId(Long customerId);
    
    /**
     * 根据条件查询客户证券持仓
     */
    @Query("SELECT p FROM SecuritiesPositionQueryEntity p WHERE " +
           "(:customerId IS NULL OR p.customerId = :customerId) AND " +
           "(:securityCode IS NULL OR p.securityCode = :securityCode) AND " +
           "(:securitiesAccountId IS NULL OR p.securitiesAccountId = :securitiesAccountId)")
    Page<SecuritiesPositionQueryEntity> findCustomerSecuritiesPositionsWithFilters(
            @Param("customerId") Long customerId,
            @Param("securityCode") String securityCode,
            @Param("securitiesAccountId") Long securitiesAccountId,
            Pageable pageable);
}