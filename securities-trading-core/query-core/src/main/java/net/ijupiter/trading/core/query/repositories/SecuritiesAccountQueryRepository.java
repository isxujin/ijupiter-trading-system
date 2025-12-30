package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.entities.SecuritiesAccountQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 证券账户查询仓储接口
 */
@Repository
public interface SecuritiesAccountQueryRepository extends JpaRepository<SecuritiesAccountQueryEntity, Long> {
    
    /**
     * 根据客户ID查询证券账户
     */
    List<SecuritiesAccountQueryEntity> findByCustomerId(Long customerId);
}