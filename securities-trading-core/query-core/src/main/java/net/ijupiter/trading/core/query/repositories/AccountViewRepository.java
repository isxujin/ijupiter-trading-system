package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.model.AccountView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 账户视图数据访问层
 * 
 * @author ijupiter
 */
@Repository
public interface AccountViewRepository extends JpaRepository<AccountView, String> {
    
    /**
     * 根据客户ID查询账户列表
     */
    List<AccountView> findByCustomerId(String customerId);
    
    /**
     * 根据账户编号查询账户
     */
    Optional<AccountView> findByAccountNo(String accountNo);
    
    /**
     * 根据账户类型查询账户列表
     */
    List<AccountView> findByAccountType(String accountType);
    
    /**
     * 根据账户状态查询账户列表
     */
    List<AccountView> findByStatus(String status);
    
    /**
     * 根据客户ID和账户类型查询账户列表
     */
    List<AccountView> findByCustomerIdAndAccountType(String customerId, String accountType);
    
    /**
     * 根据客户ID和账户状态查询账户列表
     */
    List<AccountView> findByCustomerIdAndStatus(String customerId, String status);
    
    /**
     * 统计客户的账户数量
     */
    @Query("SELECT COUNT(a) FROM AccountView a WHERE a.customerId = :customerId")
    long countByCustomerId(@Param("customerId") String customerId);
}