package net.ijupiter.trading.core.customer.repositories;

import net.ijupiter.trading.core.customer.entities.FundAccountBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 资金账户余额数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface FundAccountBalanceRepository extends JpaRepository<FundAccountBalanceEntity, String> {

    /**
     * 根据账户ID查询资金账户余额
     * 
     * @param accountId 账户ID
     * @return 资金账户余额实体
     */
    Optional<FundAccountBalanceEntity> findByAccountId(String accountId);

    /**
     * 根据账户编号查询资金账户余额
     * 
     * @param accountCode 账户编号
     * @return 资金账户余额实体
     */
    FundAccountBalanceEntity findByAccountCode(String accountCode);

    /**
     * 根据客户ID查询资金账户余额
     * 
     * @param customerId 客户ID
     * @return 资金账户余额列表
     */
    List<FundAccountBalanceEntity> findByCustomerId(String customerId);

    /**
     * 查询指定时间范围内更新的资金账户余额
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 资金账户余额列表
     */
    @Query("SELECT fab FROM FundAccountBalanceEntity fab WHERE fab.lastUpdateTime >= :startTime AND fab.lastUpdateTime <= :endTime")
    List<FundAccountBalanceEntity> findByLastUpdateTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 查询余额大于指定金额的资金账户余额
     * 
     * @param amount 金额
     * @return 资金账户余额列表
     */
    @Query("SELECT fab FROM FundAccountBalanceEntity fab WHERE fab.totalBalance > :amount")
    List<FundAccountBalanceEntity> findByTotalBalanceGreaterThan(@Param("amount") java.math.BigDecimal amount);

    /**
     * 统计客户的资金账户余额记录数量
     * 
     * @param customerId 客户ID
     * @return 资金账户余额记录数量
     */
    @Query("SELECT COUNT(fab) FROM FundAccountBalanceEntity fab WHERE fab.customerId = :customerId")
    long countByCustomerId(@Param("customerId") String customerId);

    /**
     * 计算客户总余额
     * 
     * @param customerId 客户ID
     * @return 总余额
     */
    @Query("SELECT SUM(fab.totalBalance) FROM FundAccountBalanceEntity fab WHERE fab.customerId = :customerId")
    java.math.BigDecimal sumTotalBalanceByCustomerId(@Param("customerId") String customerId);

    /**
     * 计算客户总可用余额
     * 
     * @param customerId 客户ID
     * @return 总可用余额
     */
    @Query("SELECT SUM(fab.availableBalance) FROM FundAccountBalanceEntity fab WHERE fab.customerId = :customerId")
    java.math.BigDecimal sumAvailableBalanceByCustomerId(@Param("customerId") String customerId);
}