package net.ijupiter.trading.core.customer.repositories;

import net.ijupiter.trading.core.customer.entities.TradingAccountPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 交易账户持仓数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface TradingAccountPositionRepository extends JpaRepository<TradingAccountPositionEntity, String> {

    /**
     * 根据账户ID查询交易账户持仓
     * 
     * @param accountId 账户ID
     * @return 交易账户持仓实体
     */
    Optional<TradingAccountPositionEntity> findByAccountId(String accountId);

    /**
     * 根据账户编号查询交易账户持仓
     * 
     * @param accountCode 账户编号
     * @return 交易账户持仓实体
     */
    TradingAccountPositionEntity findByAccountCode(String accountCode);

    /**
     * 根据客户ID查询交易账户持仓
     * 
     * @param customerId 客户ID
     * @return 交易账户持仓列表
     */
    List<TradingAccountPositionEntity> findByCustomerId(String customerId);

    /**
     * 查询指定时间范围内更新的交易账户持仓
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易账户持仓列表
     */
    @Query("SELECT tap FROM TradingAccountPositionEntity tap WHERE tap.lastUpdateTime >= :startTime AND tap.lastUpdateTime <= :endTime")
    List<TradingAccountPositionEntity> findByLastUpdateTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 查询持仓份额大于指定数量的交易账户持仓
     * 
     * @param shares 数量
     * @return 交易账户持仓列表
     */
    @Query("SELECT tap FROM TradingAccountPositionEntity tap WHERE tap.positionShares > :shares")
    List<TradingAccountPositionEntity> findByPositionSharesGreaterThan(@Param("shares") java.math.BigDecimal shares);

    /**
     * 查询有持仓份额的交易账户持仓
     * 
     * @return 交易账户持仓列表
     */
    @Query("SELECT tap FROM TradingAccountPositionEntity tap WHERE tap.positionShares > 0")
    List<TradingAccountPositionEntity> findByPositionSharesGreaterThanZero();

    /**
     * 统计客户的交易账户持仓记录数量
     * 
     * @param customerId 客户ID
     * @return 交易账户持仓记录数量
     */
    @Query("SELECT COUNT(tap) FROM TradingAccountPositionEntity tap WHERE tap.customerId = :customerId")
    long countByCustomerId(@Param("customerId") String customerId);

    /**
     * 计算客户总持仓份额
     * 
     * @param customerId 客户ID
     * @return 总持仓份额
     */
    @Query("SELECT SUM(tap.positionShares) FROM TradingAccountPositionEntity tap WHERE tap.customerId = :customerId")
    java.math.BigDecimal sumPositionSharesByCustomerId(@Param("customerId") String customerId);

    /**
     * 计算客户总可用份额
     * 
     * @param customerId 客户ID
     * @return 总可用份额
     */
    @Query("SELECT SUM(tap.availableShares) FROM TradingAccountPositionEntity tap WHERE tap.customerId = :customerId")
    java.math.BigDecimal sumAvailableSharesByCustomerId(@Param("customerId") String customerId);

    /**
     * 计算客户总持仓市值
     * 
     * @param customerId 客户ID
     * @return 总持仓市值
     */
    @Query("SELECT SUM(tap.totalMarketValue) FROM TradingAccountPositionEntity tap WHERE tap.customerId = :customerId")
    java.math.BigDecimal sumTotalMarketValueByCustomerId(@Param("customerId") String customerId);
}