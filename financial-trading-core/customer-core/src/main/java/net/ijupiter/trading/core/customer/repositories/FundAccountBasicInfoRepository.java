package net.ijupiter.trading.core.customer.repositories;

import net.ijupiter.trading.core.customer.entities.FundAccountBasicInfoEntity;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.enums.FundAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 资金账户基本信息数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface FundAccountBasicInfoRepository extends JpaRepository<FundAccountBasicInfoEntity, String> {

    /**
     * 根据账户ID查询资金账户基本信息
     * 
     * @param accountId 账户ID
     * @return 资金账户基本信息实体
     */
    Optional<FundAccountBasicInfoEntity> findByAccountId(String accountId);

    /**
     * 根据账户编号查询资金账户基本信息
     * 
     * @param accountCode 账户编号
     * @return 资金账户基本信息实体
     */
    FundAccountBasicInfoEntity findByAccountCode(String accountCode);

    /**
     * 根据客户ID查询资金账户基本信息
     * 
     * @param customerId 客户ID
     * @return 资金账户基本信息列表
     */
    List<FundAccountBasicInfoEntity> findByCustomerId(String customerId);

    /**
     * 根据客户ID和账户状态查询资金账户基本信息
     * 
     * @param customerId 客户ID
     * @param accountStatus 账户状态
     * @return 资金账户基本信息列表
     */
    List<FundAccountBasicInfoEntity> findByCustomerIdAndAccountStatus(String customerId, AccountStatus accountStatus);

    /**
     * 根据客户ID和账户类型查询资金账户基本信息
     * 
     * @param customerId 客户ID
     * @param accountType 账户类型
     * @return 资金账户基本信息列表
     */
    List<FundAccountBasicInfoEntity> findByCustomerIdAndAccountType(String customerId, FundAccountType accountType);

    /**
     * 根据银行卡号查询资金账户基本信息
     * 
     * @param bankCardNumber 银行卡号
     * @return 资金账户基本信息列表
     */
    List<FundAccountBasicInfoEntity> findByBankCardNumber(String bankCardNumber);

    /**
     * 检查客户是否已有指定类型的资金账户基本信息
     * 
     * @param customerId 客户ID
     * @param accountType 账户类型
     * @return 是否存在
     */
    boolean existsByCustomerIdAndAccountType(String customerId, FundAccountType accountType);

    /**
     * 检查账户编号是否已存在
     * 
     * @param accountCode 账户编号
     * @return 是否存在
     */
    boolean existsByAccountCode(String accountCode);

    /**
     * 查询指定时间范围内创建的资金账户基本信息
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 资金账户基本信息列表
     */
    @Query("SELECT fabi FROM FundAccountBasicInfoEntity fabi WHERE fabi.createTime >= :startTime AND fabi.createTime <= :endTime")
    List<FundAccountBasicInfoEntity> findByCreateTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 统计客户的资金账户基本信息数量
     * 
     * @param customerId 客户ID
     * @return 资金账户基本信息数量
     */
    @Query("SELECT COUNT(fabi) FROM FundAccountBasicInfoEntity fabi WHERE fabi.customerId = :customerId")
    long countByCustomerId(@Param("customerId") String customerId);
}