package net.ijupiter.trading.core.trading.repositories;

import net.ijupiter.trading.core.trading.entities.TradingEngineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易引擎JPA数据访问接口
 */
@Repository
public interface TradingEngineJpaRepository extends JpaRepository<TradingEngineEntity, Long> {
    
    /**
     * 根据交易编号查询交易记录
     */
    TradingEngineEntity findByTradeCode(String tradeCode);
    
    /**
     * 根据客户ID查询交易记录
     */
    List<TradingEngineEntity> findByCustomerId(Long customerId);
    
    /**
     * 根据状态查询交易记录
     */
    List<TradingEngineEntity> findByStatus(Integer status);
    
    /**
     * 根据证券代码查询交易记录
     */
    List<TradingEngineEntity> findBySecurityCode(String securityCode);
    
    /**
     * 根据交易类型查询交易记录
     */
    List<TradingEngineEntity> findByTradeType(Integer tradeType);
    
    /**
     * 根据交易市场查询交易记录
     */
    List<TradingEngineEntity> findByMarket(Integer market);
    
    /**
     * 根据订单编号查询交易记录
     */
    List<TradingEngineEntity> findByOrderCode(String orderCode);
    
    /**
     * 根据交易日期范围查询交易记录(基于成交时间)
     */
    @Query("SELECT t FROM TradingEngineEntity t WHERE t.executeTime BETWEEN :startDate AND :endDate")
    List<TradingEngineEntity> findByExecuteTimeBetween(@Param("startDate") LocalDateTime startDate, 
                                                     @Param("endDate") LocalDateTime endDate);
    
    /**
     * 统计交易记录数量
     */
    @Query("SELECT COUNT(t) FROM TradingEngineEntity t")
    long countAll();
    
    /**
     * 根据交易类型统计记录数量
     */
    @Query("SELECT COUNT(t) FROM TradingEngineEntity t WHERE t.tradeType = :tradeType")
    long countByTradeType(@Param("tradeType") Integer tradeType);
    
    /**
     * 根据状态统计记录数量
     */
    @Query("SELECT COUNT(t) FROM TradingEngineEntity t WHERE t.status = :status")
    long countByStatus(@Param("status") Integer status);
    
    /**
     * 统计总交易金额
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TradingEngineEntity t")
    java.math.BigDecimal sumAllAmount();
    
    /**
     * 统计总手续费
     */
    @Query("SELECT COALESCE(SUM(t.fee), 0) FROM TradingEngineEntity t")
    java.math.BigDecimal sumAllFee();
    
    /**
     * 统计买入交易数量
     */
    @Query("SELECT COUNT(t) FROM TradingEngineEntity t WHERE t.tradeType = 1")
    long countBuyTrades();
    
    /**
     * 统计卖出交易数量
     */
    @Query("SELECT COUNT(t) FROM TradingEngineEntity t WHERE t.tradeType = 2")
    long countSellTrades();
}