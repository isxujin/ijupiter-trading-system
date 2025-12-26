package net.ijupiter.trading.core.trading.services;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.trading.models.TradingStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ijupiter.trading.api.trading.commands.CreateTradeCommand;
import net.ijupiter.trading.api.trading.commands.MatchTradeCommand;
import net.ijupiter.trading.api.trading.commands.ExecuteTradeCommand;
import net.ijupiter.trading.api.trading.commands.CancelTradeCommand;
import net.ijupiter.trading.api.trading.dtos.TradingEngineDTO;
import net.ijupiter.trading.api.trading.services.TradingEngineService;
import net.ijupiter.trading.core.trading.repositories.TradingEngineRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 交易引擎领域服务实现
 */
@Slf4j
@Service
@Transactional
public class TradingEngineDomainService implements TradingEngineService {
    
    @Autowired
    private TradingEngineRepository tradingEngineRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    @Override
    public List<TradingEngineDTO> findAll() {
        return tradingEngineRepository.findAllTrades();
    }
    
    @Override
    public Optional<TradingEngineDTO> findById(Long id) {
        return tradingEngineRepository.findTradeById(id);
    }
    
    @Override
    public TradingEngineDTO save(TradingEngineDTO tradingEngineDTO) {
        return tradingEngineRepository.saveTrade(tradingEngineDTO);
    }
    
    @Override
    public void deleteById(Long id) {
        tradingEngineRepository.deleteTradeById(id);
    }
    
    @Override
    public void delete(TradingEngineDTO entity) {
        tradingEngineRepository.deleteTradeById(entity.getId());
    }
    
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("不支持批量删除所有交易记录");
    }
    
    @Override
    public TradingEngineDTO saveAndFlush(TradingEngineDTO entity) {
        return tradingEngineRepository.saveTrade(entity);
    }
    
    @Override
    public List<TradingEngineDTO> saveAll(List<TradingEngineDTO> entities) {
        return entities.stream()
                .map(tradingEngineRepository::saveTrade)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TradingEngineDTO> findAllById(List<Long> ids) {
        return ids.stream()
                .map(tradingEngineRepository::findTradeById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsById(Long id) {
        return tradingEngineRepository.findTradeById(id).isPresent();
    }
    
    @Override
    public long count() {
        return tradingEngineRepository.findAllTrades().size();
    }
    
    @Override
    public List<TradingEngineDTO> findByCustomerId(Long customerId) {
        return tradingEngineRepository.findTradesByCustomerId(customerId);
    }
    
    @Override
    public List<TradingEngineDTO> findByStatus(Integer status) {
        return tradingEngineRepository.findTradesByStatus(status);
    }
    
    @Override
    public List<TradingEngineDTO> findByTradeDate(LocalDateTime startDate, LocalDateTime endDate) {
        return tradingEngineRepository.findTradesByDateRange(startDate, endDate);
    }
    
    @Override
    public List<TradingEngineDTO> findBySecurityCode(String securityCode) {
        return tradingEngineRepository.findTradesBySecurityCode(securityCode);
    }
    
    @Override
    public List<TradingEngineDTO> findByTradeType(Integer tradeType) {
        return tradingEngineRepository.findTradesByTradeType(tradeType);
    }
    
    @Override
    public List<TradingEngineDTO> findByMarket(Integer market) {
        return tradingEngineRepository.findTradesByMarket(market);
    }
    
    @Override
    public Optional<TradingEngineDTO> findByTradeCode(String tradeCode) {
        return tradingEngineRepository.findTradeByCode(tradeCode);
    }
    
    @Override
    public List<TradingEngineDTO> findByOrderCode(String orderCode) {
        return tradingEngineRepository.findTradesByOrderCode(orderCode);
    }
    
    @Override
    public TradingStatistics getTradingStatistics() {
        List<TradingEngineDTO> allTrades = tradingEngineRepository.findAllTrades();
        
        long totalTrades = allTrades.size();
        long pendingTrades = allTrades.stream()
                .filter(t -> t.getStatus() == 1)
                .count();
        long partialTrades = allTrades.stream()
                .filter(t -> t.getStatus() == 2)
                .count();
        long completedTrades = allTrades.stream()
                .filter(t -> t.getStatus() == 3)
                .count();
        long cancelledTrades = allTrades.stream()
                .filter(t -> t.getStatus() == 4)
                .count();
        
        BigDecimal totalAmount = allTrades.stream()
                .map(t -> t.getAmount() != null ? t.getAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalFee = allTrades.stream()
                .map(t -> t.getFee() != null ? t.getFee() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        long buyTrades = allTrades.stream()
                .filter(t -> t.getTradeType() == 1)
                .count();
        
        long sellTrades = allTrades.stream()
                .filter(t -> t.getTradeType() == 2)
                .count();
        
        // 今日交易(简化处理，实际应该从数据库查询)
        long todayTrades = 0;
        BigDecimal todayTradeAmount = BigDecimal.ZERO;
        
        return new TradingStatistics(totalTrades, pendingTrades, partialTrades,
                completedTrades, cancelledTrades, totalAmount, totalFee,
                buyTrades, sellTrades, todayTrades, todayTradeAmount);
    }
    
    /**
     * 创建交易(通过命令)
     */
    public CompletableFuture<String> createTrade(CreateTradeCommand command) {
        log.info("发送创建交易命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 撮合交易(通过命令)
     */
    public CompletableFuture<Void> matchTrade(MatchTradeCommand command) {
        log.info("发送撮合交易命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 执行交易(通过命令)
     */
    public CompletableFuture<Void> executeTrade(ExecuteTradeCommand command) {
        log.info("发送执行交易命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 取消交易(通过命令)
     */
    public CompletableFuture<Void> cancelTrade(CancelTradeCommand command) {
        log.info("发送取消交易命令: {}", command);
        return commandGateway.send(command);
    }
}