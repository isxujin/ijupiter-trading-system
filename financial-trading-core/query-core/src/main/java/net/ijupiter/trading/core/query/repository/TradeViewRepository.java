package net.ijupiter.trading.core.query.repository;

import net.ijupiter.trading.core.query.model.TradeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 成交记录视图数据访问层
 * 
 * @author ijupiter
 */
@Repository
public interface TradeViewRepository extends JpaRepository<TradeView, String> {
    
    /**
     * 根据订单ID查询成交记录列表
     */
    List<TradeView> findByOrderId(String orderId);
    
    /**
     * 根据客户ID查询成交记录列表
     */
    List<TradeView> findByCustomerId(String customerId);
    
    /**
     * 根据账户ID查询成交记录列表
     */
    List<TradeView> findByAccountId(String accountId);
    
    /**
     * 根据产品代码查询成交记录列表
     */
    List<TradeView> findByProductCode(String productCode);
    
    /**
     * 根据成交方向查询成交记录列表
     */
    List<TradeView> findBySide(String side);
    
    /**
     * 根据交易市场查询成交记录列表
     */
    List<TradeView> findByMarket(String market);
    
    /**
     * 根据时间范围查询成交记录列表
     */
    @Query("SELECT t FROM TradeView t WHERE t.tradeTime BETWEEN :startTime AND :endTime")
    List<TradeView> findByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                   @Param("endTime") LocalDateTime endTime);
    
    /**
     * 根据客户ID和产品代码查询成交记录列表
     */
    List<TradeView> findByCustomerIdAndProductCode(String customerId, String productCode);
    
    /**
     * 统计客户的成交记录数量
     */
    @Query("SELECT COUNT(t) FROM TradeView t WHERE t.customerId = :customerId")
    long countByCustomerId(@Param("customerId") String customerId);
    
    /**
     * 计算客户总成交金额
     */
    @Query("SELECT SUM(t.amount) FROM TradeView t WHERE t.customerId = :customerId")
    Double sumAmountByCustomerId(@Param("customerId") String customerId);
    
    /**
     * 计算产品总成交金额
     */
    @Query("SELECT SUM(t.amount) FROM TradeView t WHERE t.productCode = :productCode")
    Double sumAmountByProductCode(@Param("productCode") String productCode);
}