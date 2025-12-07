package net.ijupiter.trading.core.customer.repositories;

import net.ijupiter.trading.core.customer.entities.TradingAccountBasicInfoEntity;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.customer.enums.TradingAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 交易账户基本信息数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface TradingAccountBasicInfoRepository extends JpaRepository<TradingAccountBasicInfoEntity, String> {

    /**
     * 根据账户ID查询交易账户基本信息
     * 
     * @param accountId 账户ID
     * @return 交易账户基本信息实体
     */
    Optional<TradingAccountBasicInfoEntity> findByAccountId(String accountId);

    /**
     * 根据账户编号查询交易账户基本信息
     * 
     * @param accountCode 账户编号
     * @return 交易账户基本信息实体
     */
    TradingAccountBasicInfoEntity findByAccountCode(String accountCode);

    /**
     * 根据客户ID查询交易账户基本信息
     * 
     * @param customerId 客户ID
     * @return 交易账户基本信息列表
     */
    List<TradingAccountBasicInfoEntity> findByCustomerId(String customerId);

    /**
     * 根据客户ID和账户状态查询交易账户基本信息
     * 
     * @param customerId 客户ID
     * @param accountStatus 账户状态
     * @return 交易账户基本信息列表
     */
    List<TradingAccountBasicInfoEntity> findByCustomerIdAndAccountStatus(String customerId, AccountStatus accountStatus);

    /**
     * 根据客户ID和账户类型查询交易账户基本信息
     * 
     * @param customerId 客户ID
     * @param accountType 账户类型
     * @return 交易账户基本信息列表
     */
    List<TradingAccountBasicInfoEntity> findByCustomerIdAndAccountType(String customerId, TradingAccountType accountType);

    /**
     * 根据交易所代码查询交易账户基本信息
     * 
     * @param exchangeCode 交易所代码
     * @return 交易账户基本信息列表
     */
    List<TradingAccountBasicInfoEntity> findByExchangeCode(String exchangeCode);

    /**
     * 根据交易所账号查询交易账户基本信息
     * 
     * @param exchangeAccountNumber 交易所账号
     * @return 交易账户基本信息实体
     */
    TradingAccountBasicInfoEntity findByExchangeAccountNumber(String exchangeAccountNumber);

    /**
     * 检查客户是否已有指定类型的交易账户基本信息
     * 
     * @param customerId 客户ID
     * @param accountType 账户类型
     * @return 是否存在
     */
    boolean existsByCustomerIdAndAccountType(String customerId, TradingAccountType accountType);

    /**
     * 检查账户编号是否已存在
     * 
     * @param accountCode 账户编号
     * @return 是否存在
     */
    boolean existsByAccountCode(String accountCode);

    /**
     * 查询指定时间范围内创建的交易账户基本信息
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易账户基本信息列表
     */
    @Query("SELECT tabi FROM TradingAccountBasicInfoEntity tabi WHERE tabi.createTime >= :startTime AND tabi.createTime <= :endTime")
    List<TradingAccountBasicInfoEntity> findByCreateTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 统计客户的交易账户基本信息数量
     * 
     * @param customerId 客户ID
     * @return 交易账户基本信息数量
     */
    @Query("SELECT COUNT(tabi) FROM TradingAccountBasicInfoEntity tabi WHERE tabi.customerId = :customerId")
    long countByCustomerId(@Param("customerId") String customerId);
}