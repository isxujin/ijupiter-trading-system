package net.ijupiter.trading.core.securities.repositories;

import net.ijupiter.trading.core.securities.entities.SecuritiesPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 证券持仓JPA数据访问接口
 */
@Repository
public interface SecuritiesPositionJpaRepository extends JpaRepository<SecuritiesPositionEntity, Long> {
    
    /**
     * 根据账户编号查询持仓
     */
    List<SecuritiesPositionEntity> findByAccountCode(String accountCode);
    
    /**
     * 根据客户ID查询持仓
     */
    List<SecuritiesPositionEntity> findByCustomerId(Long customerId);
    
    /**
     * 根据证券代码查询持仓
     */
    List<SecuritiesPositionEntity> findBySecurityCode(String securityCode);
    
    /**
     * 根据账户编号和证券代码查询持仓
     */
    SecuritiesPositionEntity findByAccountCodeAndSecurityCode(String accountCode, String securityCode);
    
    /**
     * 统计持仓数量
     */
    @Query("SELECT COUNT(p) FROM SecuritiesPositionEntity p")
    long countAll();
    
    /**
     * 统计总市值
     */
    @Query("SELECT COALESCE(SUM(p.marketValue), 0) FROM SecuritiesPositionEntity p")
    BigDecimal sumAllMarketValue();
    
    /**
     * 统计总盈亏
     */
    @Query("SELECT COALESCE(SUM(p.profitLoss), 0) FROM SecuritiesPositionEntity p")
    BigDecimal sumAllProfitLoss();
}