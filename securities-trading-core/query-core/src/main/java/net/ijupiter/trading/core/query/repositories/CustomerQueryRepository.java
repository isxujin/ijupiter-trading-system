package net.ijupiter.trading.core.query.repositories;

import net.ijupiter.trading.core.query.entities.CustomerQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 客户查询仓储接口
 */
@Repository
public interface CustomerQueryRepository extends JpaRepository<CustomerQueryEntity, Long> {
    /**
     * 根据客户ID查询客户信息
     */
    Optional<CustomerQueryEntity> findById(Long customerId);
    
    /**
     * 根据客户编号查询客户信息
     */
    Optional<CustomerQueryEntity> findByCustomerNo(String customerNo);
    
    /**
     * 根据客户状态查询客户列表
     */
    List<CustomerQueryEntity> findByStatus(Integer status);
    
    /**
     * 根据客户类型查询客户列表
     */
    List<CustomerQueryEntity> findByType(String type);
}