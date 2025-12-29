package net.ijupiter.trading.core.funding.repositories;

import net.ijupiter.trading.core.funding.entities.FundingAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金JPA数据访问接口
 */
@Repository
public interface FundingJpaRepository extends JpaRepository<FundingAccountEntity, Long> {
    
    /**
     * 根据账户编号查询资金账户
     */
    FundingAccountEntity findByAccountCode(String accountCode);
    
    /**
     * 根据客户ID查询资金账户
     */
    List<FundingAccountEntity> findByCustomerId(Long customerId);
    
    /**
     * 根据状态查询资金账户
     */
    List<FundingAccountEntity> findByStatus(Integer status);
    
    /**
     * 统计账户数量
     */
    @Query("SELECT COUNT(a) FROM FundingAccountEntity a")
    long countAll();
    
    /**
     * 根据状态统计账户数量
     */
    @Query("SELECT COUNT(a) FROM FundingAccountEntity a WHERE a.status = :status")
    long countByStatus(@Param("status") Integer status);
    
    /**
     * 统计总余额
     */
    @Query("SELECT COALESCE(SUM(a.balance), 0) FROM FundingAccountEntity a")
    BigDecimal sumAllBalance();
    
    /**
     * 统计总冻结金额
     */
    @Query("SELECT COALESCE(SUM(a.frozenAmount), 0) FROM FundingAccountEntity a")
    BigDecimal sumAllFrozenAmount();
    
    /**
     * 统计总可用余额
     */
    @Query("SELECT COALESCE(SUM(a.availableBalance), 0) FROM FundingAccountEntity a")
    BigDecimal sumAllAvailableBalance();

}