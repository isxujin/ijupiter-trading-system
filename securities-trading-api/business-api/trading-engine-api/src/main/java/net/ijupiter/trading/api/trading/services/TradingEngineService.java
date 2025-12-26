package net.ijupiter.trading.api.trading.services;

import net.ijupiter.trading.api.trading.models.TradingStatistics;
import net.ijupiter.trading.common.services.BaseService;
import net.ijupiter.trading.api.trading.dtos.TradingEngineDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 交易引擎服务接口
 */
public interface TradingEngineService extends BaseService<TradingEngineDTO, Long> {
    
    /**
     * 根据客户ID查询交易记录
     */
    List<TradingEngineDTO> findByCustomerId(Long customerId);
    
    /**
     * 根据状态查询交易记录
     */
    List<TradingEngineDTO> findByStatus(Integer status);
    
    /**
     * 根据交易日期范围查询交易记录
     */
    List<TradingEngineDTO> findByTradeDate(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 根据证券代码查询交易记录
     */
    List<TradingEngineDTO> findBySecurityCode(String securityCode);
    
    /**
     * 根据交易类型查询交易记录
     */
    List<TradingEngineDTO> findByTradeType(Integer tradeType);
    
    /**
     * 根据交易市场查询交易记录
     */
    List<TradingEngineDTO> findByMarket(Integer market);
    
    /**
     * 根据交易编号查询交易记录
     */
    Optional<TradingEngineDTO> findByTradeCode(String tradeCode);
    
    /**
     * 根据订单编号查询交易记录
     */
    List<TradingEngineDTO> findByOrderCode(String orderCode);
    
    /**
     * 获取交易统计数据
     */
    TradingStatistics getTradingStatistics();
}