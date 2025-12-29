package net.ijupiter.trading.core.customer.repositories;

import net.ijupiter.trading.core.customer.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 客户JPA数据访问接口
 */
@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {
    
    /**
     * 根据客户编号查询客户
     */
    CustomerEntity findByCustomerCode(String customerCode);
    
    /**
     * 检查客户编号是否存在
     */
    boolean existsByCustomerCode(String customerCode);
    
    /**
     * 根据客户类型查询客户
     */
    java.util.List<CustomerEntity> findByCustomerType(Integer customerType);
    
    /**
     * 根据状态查询客户
     */
    java.util.List<CustomerEntity> findByStatus(Integer status);
    
    /**
     * 根据风险等级查询客户
     */
    java.util.List<CustomerEntity> findByRiskLevel(Integer riskLevel);
    
    /**
     * 根据手机号码查询客户
     */
    CustomerEntity findByMobile(String mobile);
    
    /**
     * 根据邮箱查询客户
     */
    CustomerEntity findByEmail(String email);
    
    /**
     * 根据客户名称模糊查询
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.customerName LIKE %:customerName%")
    java.util.List<CustomerEntity> findByCustomerNameContaining(@Param("customerName") String customerName);
    
    /**
     * 根据证件类型和证件号码查询客户
     */
    CustomerEntity findByIdTypeAndIdNumber(Integer idType, String idNumber);
    
    /**
     * 统计客户数量
     */
    @Query("SELECT COUNT(c) FROM CustomerEntity c")
    long countAll();
    
    /**
     * 根据客户类型统计客户数量
     */
    @Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.customerType = :customerType")
    long countByCustomerType(@Param("customerType") Integer customerType);
    
    /**
     * 根据状态统计客户数量
     */
    @Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.status = :status")
    long countByStatus(@Param("status") Integer status);
    
    /**
     * 根据风险等级统计客户数量
     */
    @Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.riskLevel = :riskLevel")
    long countByRiskLevel(@Param("riskLevel") Integer riskLevel);
}