package net.ijupiter.trading.core.customer.repositories;

import net.ijupiter.trading.core.customer.entities.CustomerEntity;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 客户数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    /**
     * 根据客户编号查询客户
     * 
     * @param customerCode 客户编号
     * @return 客户实体
     */
    Optional<CustomerEntity> findByCustomerCode(String customerCode);

    /**
     * 根据身份证号查询客户
     * 
     * @param idCardNumber 身份证号
     * @return 客户实体
     */
    Optional<CustomerEntity> findByIdCardNumber(String idCardNumber);

    /**
     * 根据手机号查询客户
     * 
     * @param phoneNumber 手机号
     * @return 客户实体
     */
    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);

    /**
     * 根据客户状态查询客户列表
     * 
     * @param customerStatus 客户状态
     * @return 客户列表
     */
    List<CustomerEntity> findByCustomerStatus(CustomerStatus customerStatus);

    /**
     * 根据风险等级查询客户列表
     * 
     * @param riskLevel 风险等级
     * @return 客户列表
     */
    List<CustomerEntity> findByRiskLevel(String riskLevel);

    /**
     * 根据客户状态和风险等级查询客户列表
     * 
     * @param customerStatus 客户状态
     * @param riskLevel 风险等级
     * @return 客户列表
     */
    List<CustomerEntity> findByCustomerStatusAndRiskLevel(CustomerStatus customerStatus, String riskLevel);

    /**
     * 查询是否激活的客户列表
     * 
     * @param isActive 是否激活
     * @return 客户列表
     */
    List<CustomerEntity> findByIsActive(Boolean isActive);

    /**
     * 查询指定时间范围内注册的客户
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 客户列表
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.createTime >= :startTime AND c.createTime <= :endTime")
    List<CustomerEntity> findByCreateTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 查询指定时间范围内注销的客户
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 客户列表
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.cancelTime >= :startTime AND c.cancelTime <= :endTime")
    List<CustomerEntity> findByCancelTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 检查身份证号是否已存在
     * 
     * @param idCardNumber 身份证号
     * @return 是否存在
     */
    boolean existsByIdCardNumber(String idCardNumber);

    /**
     * 检查手机号是否已存在
     * 
     * @param phoneNumber 手机号
     * @return 是否存在
     */
    boolean existsByPhoneNumber(String phoneNumber);

    /**
     * 检查客户编号是否已存在
     * 
     * @param customerCode 客户编号
     * @return 是否存在
     */
    boolean existsByCustomerCode(String customerCode);

    /**
     * 统计客户数量
     * 
     * @param customerStatus 客户状态
     * @return 客户数量
     */
    @Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.customerStatus = :customerStatus")
    long countByCustomerStatus(@Param("customerStatus") CustomerStatus customerStatus);

    /**
     * 统计指定风险等级的客户数量
     * 
     * @param riskLevel 风险等级
     * @return 客户数量
     */
    @Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.riskLevel = :riskLevel")
    long countByRiskLevel(@Param("riskLevel") String riskLevel);
}