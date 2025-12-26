package net.ijupiter.trading.core.trading.repositories;

import net.ijupiter.trading.api.trading.dtos.TradingEngineDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 交易引擎数据访问接口
 */
public interface TradingEngineRepository {
    
    /**
     * 查询所有交易记录
     */
    List<TradingEngineDTO> findAllTrades();
    
    /**
     * 根据ID查询交易记录
     */
    Optional<TradingEngineDTO> findTradeById(Long id);
    
    /**
     * 保存交易记录
     */
    TradingEngineDTO saveTrade(TradingEngineDTO tradingEngineDTO);
    
    /**
     * 更新交易记录
     */
    void updateTrade(TradingEngineDTO tradingEngineDTO);
    
    /**
     * 删除交易记录
     */
    void deleteTradeById(Long id);
    
    /**
     * 根据客户ID查询交易记录
     */
    List<TradingEngineDTO> findTradesByCustomerId(Long customerId);
    
    /**
     * 根据状态查询交易记录
     */
    List<TradingEngineDTO> findTradesByStatus(Integer status);
    
    /**
     * 根据交易日期范围查询交易记录
     */
    List<TradingEngineDTO> findTradesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 根据证券代码查询交易记录
     */
    List<TradingEngineDTO> findTradesBySecurityCode(String securityCode);
    
    /**
     * 根据交易类型查询交易记录
     */
    List<TradingEngineDTO> findTradesByTradeType(Integer tradeType);
    
    /**
     * 根据交易市场查询交易记录
     */
    List<TradingEngineDTO> findTradesByMarket(Integer market);
    
    /**
     * 根据交易编号查询交易记录
     */
    Optional<TradingEngineDTO> findTradeByCode(String tradeCode);
    
    /**
     * 根据订单编号查询交易记录
     */
    List<TradingEngineDTO> findTradesByOrderCode(String orderCode);
}