package net.ijupiter.trading.core.repositories;

import net.ijupiter.trading.core.entities.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易数据访问接口
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, String> {

    /**
     * 根据买单ID或卖单ID查询交易
     * 
     * @param buyOrderId 买单ID
     * @param sellOrderId 卖单ID
     * @return 交易列表
     */
    List<TradeEntity> findByBuyOrderIdOrSellOrderId(String buyOrderId, String sellOrderId);

    /**
     * 根据产品ID查询最近的交易
     * 
     * @param productId 产品ID
     * @return 最近的交易
     */
    TradeEntity findTopByProductIdOrderByTradeTimeDesc(String productId);

    /**
     * 根据产品ID和时间范围查询交易
     * 
     * @param productId 产品ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易列表
     */
    @Query("SELECT t FROM TradeEntity t WHERE t.productId = :productId " +
           "AND t.tradeTime >= :startTime AND t.tradeTime <= :endTime " +
           "ORDER BY t.tradeTime DESC")
    List<TradeEntity> findByProductIdAndTradeTimeBetweenOrderByTradeTimeDesc(
            @Param("productId") String productId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * 根据产品ID查询指定数量的最新交易
     * 
     * @param productId 产品ID
     * @param limit 数量限制
     * @return 交易列表
     */
    @Query("SELECT t FROM TradeEntity t WHERE t.productId = :productId " +
           "ORDER BY t.tradeTime DESC")
    List<TradeEntity> findTopByProductIdOrderByTradeTimeDesc(String productId, int limit);

    /**
     * 查询指定时间范围内的所有交易
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 交易列表
     */
    @Query("SELECT t FROM TradeEntity t WHERE t.tradeTime >= :startTime " +
           "AND t.tradeTime <= :endTime ORDER BY t.tradeTime DESC")
    List<TradeEntity> findByTradeTimeBetweenOrderByTradeTimeDesc(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}