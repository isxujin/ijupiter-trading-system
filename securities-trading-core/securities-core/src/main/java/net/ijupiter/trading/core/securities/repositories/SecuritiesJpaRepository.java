package net.ijupiter.trading.core.securities.repositories;

import net.ijupiter.trading.core.securities.entities.SecuritiesAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 证券JPA数据访问接口
 */
@Repository
public interface SecuritiesJpaRepository extends JpaRepository<SecuritiesAccountEntity, Long> {
    
    /**
     * 根据账户编号查询证券账户
     */
    SecuritiesAccountEntity findByAccountCode(String accountCode);
    
    /**
     * 根据客户ID查询证券账户
     */
    List<SecuritiesAccountEntity> findByCustomerId(Long customerId);
    
    /**
     * 根据账户类型查询证券账户
     */
    List<SecuritiesAccountEntity> findByAccountType(Integer accountType);
    
    /**
     * 根据状态查询证券账户
     */
    List<SecuritiesAccountEntity> findByStatus(Integer status);
    
    /**
     * 统计账户数量
     */
    @Query("SELECT COUNT(a) FROM SecuritiesAccountEntity a")
    long countAll();
    
    /**
     * 根据账户类型统计账户数量
     */
    @Query("SELECT COUNT(a) FROM SecuritiesAccountEntity a WHERE a.accountType = :accountType")
    long countByAccountType(@Param("accountType") Integer accountType);
    
    /**
     * 根据状态统计账户数量
     */
    @Query("SELECT COUNT(a) FROM SecuritiesAccountEntity a WHERE a.status = :status")
    long countByStatus(@Param("status") Integer status);
}