package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.model.FundAccountView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 资金账户视图数据访问层
 * 
 * @author ijupiter
 */
@Repository
public interface FundAccountViewRepository extends JpaRepository<FundAccountView, String> {
    
    /**
     * 根据客户ID查询资金账户列表
     */
    List<FundAccountView> findByCustomerId(String customerId);
    
    /**
     * 根据账户ID查询资金账户
     */
    Optional<FundAccountView> findByAccountId(String accountId);
    
    /**
     * 根据账户类型查询资金账户列表
     */
    List<FundAccountView> findByAccountType(String accountType);
    
    /**
     * 根据账户状态查询资金账户列表
     */
    List<FundAccountView> findByStatus(String status);
    
    /**
     * 根据币种查询资金账户列表
     */
    List<FundAccountView> findByCurrency(String currency);
    
    /**
     * 根据客户ID和账户类型查询资金账户列表
     */
    List<FundAccountView> findByCustomerIdAndAccountType(String customerId, String accountType);
    
    /**
     * 根据客户ID和账户状态查询资金账户列表
     */
    List<FundAccountView> findByCustomerIdAndStatus(String customerId, String status);
    
    /**
     * 统计客户的资金账户数量
     */
    @Query("SELECT COUNT(f) FROM FundAccountView f WHERE f.customerId = :customerId")
    long countByCustomerId(@Param("customerId") String customerId);
    
    /**
     * 计算客户总资产
     */
    @Query("SELECT SUM(f.totalAssets) FROM FundAccountView f WHERE f.customerId = :customerId")
    Double sumTotalAssetsByCustomerId(@Param("customerId") String customerId);
}