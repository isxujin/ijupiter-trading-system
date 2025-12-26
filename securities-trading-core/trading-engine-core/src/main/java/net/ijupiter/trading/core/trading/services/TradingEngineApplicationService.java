package net.ijupiter.trading.core.trading.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ijupiter.trading.api.trading.dtos.TradingEngineDTO;
import net.ijupiter.trading.api.trading.models.TradingStatistics;
import net.ijupiter.trading.api.trading.services.TradingEngineService;
import net.ijupiter.trading.api.trading.commands.CreateTradeCommand;
import net.ijupiter.trading.api.trading.commands.MatchTradeCommand;
import net.ijupiter.trading.api.trading.commands.ExecuteTradeCommand;
import net.ijupiter.trading.api.trading.commands.CancelTradeCommand;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 交易引擎应用服务实现
 */
@Slf4j
@Service
@Transactional
public class TradingEngineApplicationService implements TradingEngineService {
    
    @Autowired
    private TradingEngineDomainService tradingEngineDomainService;
    
    @Override
    public List<TradingEngineDTO> findAll() {
        return tradingEngineDomainService.findAll();
    }
    
    @Override
    public Optional<TradingEngineDTO> findById(Long id) {
        return tradingEngineDomainService.findById(id);
    }
    
    @Override
    public TradingEngineDTO save(TradingEngineDTO tradingEngineDTO) {
        return tradingEngineDomainService.save(tradingEngineDTO);
    }
    
    @Override
    public void deleteById(Long id) {
        tradingEngineDomainService.deleteById(id);
    }
    
    @Override
    public void delete(TradingEngineDTO entity) {
        tradingEngineDomainService.delete(entity);
    }
    
    @Override
    public void deleteAll() {
        tradingEngineDomainService.deleteAll();
    }
    
    @Override
    public TradingEngineDTO saveAndFlush(TradingEngineDTO entity) {
        return tradingEngineDomainService.saveAndFlush(entity);
    }
    
    @Override
    public List<TradingEngineDTO> saveAll(List<TradingEngineDTO> entities) {
        return tradingEngineDomainService.saveAll(entities);
    }
    
    @Override
    public List<TradingEngineDTO> findAllById(List<Long> ids) {
        return tradingEngineDomainService.findAllById(ids);
    }
    
    @Override
    public boolean existsById(Long id) {
        return tradingEngineDomainService.existsById(id);
    }
    
    @Override
    public long count() {
        return tradingEngineDomainService.count();
    }
    
    @Override
    public List<TradingEngineDTO> findByCustomerId(Long customerId) {
        return tradingEngineDomainService.findByCustomerId(customerId);
    }
    
    @Override
    public List<TradingEngineDTO> findByStatus(Integer status) {
        return tradingEngineDomainService.findByStatus(status);
    }
    
    @Override
    public List<TradingEngineDTO> findByTradeDate(LocalDateTime startDate, LocalDateTime endDate) {
        return tradingEngineDomainService.findByTradeDate(startDate, endDate);
    }
    
    @Override
    public List<TradingEngineDTO> findBySecurityCode(String securityCode) {
        return tradingEngineDomainService.findBySecurityCode(securityCode);
    }
    
    @Override
    public List<TradingEngineDTO> findByTradeType(Integer tradeType) {
        return tradingEngineDomainService.findByTradeType(tradeType);
    }
    
    @Override
    public List<TradingEngineDTO> findByMarket(Integer market) {
        return tradingEngineDomainService.findByMarket(market);
    }
    
    @Override
    public Optional<TradingEngineDTO> findByTradeCode(String tradeCode) {
        return tradingEngineDomainService.findByTradeCode(tradeCode);
    }
    
    @Override
    public List<TradingEngineDTO> findByOrderCode(String orderCode) {
        return tradingEngineDomainService.findByOrderCode(orderCode);
    }
    
    @Override
    public TradingStatistics getTradingStatistics() {
        return tradingEngineDomainService.getTradingStatistics();
    }
    
    /**
     * 创建交易
     */
    public CompletableFuture<String> createTrade(CreateTradeCommand command) {
        return tradingEngineDomainService.createTrade(command);
    }
    
    /**
     * 撮合交易
     */
    public CompletableFuture<Void> matchTrade(String tradeCode, Long buyerCustomerId, Long sellerCustomerId,
                                             java.math.BigDecimal matchPrice, java.math.BigDecimal matchQuantity,
                                             Long operatorId) {
        MatchTradeCommand command = MatchTradeCommand.builder()
                .tradeCode(tradeCode)
                .buyerCustomerId(buyerCustomerId)
                .sellerCustomerId(sellerCustomerId)
                .matchPrice(matchPrice)
                .matchQuantity(matchQuantity)
                .operatorId(operatorId)
                .build();
        return tradingEngineDomainService.matchTrade(command);
    }
    
    /**
     * 执行交易
     */
    public CompletableFuture<Void> executeTrade(String tradeCode, java.math.BigDecimal executePrice,
                                             java.math.BigDecimal executeQuantity, Long operatorId) {
        ExecuteTradeCommand command = ExecuteTradeCommand.builder()
                .tradeCode(tradeCode)
                .executePrice(executePrice)
                .executeQuantity(executeQuantity)
                .operatorId(operatorId)
                .build();
        return tradingEngineDomainService.executeTrade(command);
    }
    
    /**
     * 取消交易
     */
    public CompletableFuture<Void> cancelTrade(String tradeCode, String reason, Long operatorId) {
        CancelTradeCommand command = CancelTradeCommand.builder()
                .tradeCode(tradeCode)
                .reason(reason)
                .operatorId(operatorId)
                .build();
        return tradingEngineDomainService.cancelTrade(command);
    }
    
    /**
     * 批量撮合交易
     */
    public CompletableFuture<Void> batchMatchTrades(List<String> tradeCodes, Long operatorId) {
        log.info("开始批量撮合交易: {}", tradeCodes);
        
        // 这里可以添加更复杂的撮合逻辑，比如优先级排序、价格优先等
        // 简化处理，随机分配买卖方
        List<CompletableFuture<Void>> futures = tradeCodes.stream()
                .map(code -> {
                    // 模拟撮合过程
                    Long buyerId = (long)(Math.random() * 1000) + 1000;
                    Long sellerId = (long)(Math.random() * 1000) + 2000;
                    java.math.BigDecimal price = java.math.BigDecimal.valueOf(Math.random() * 100);
                    java.math.BigDecimal quantity = java.math.BigDecimal.valueOf(Math.random() * 1000);
                    
                    return matchTrade(code, buyerId, sellerId, price, quantity, operatorId);
                })
                .collect(java.util.stream.Collectors.toList());
        
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }
}