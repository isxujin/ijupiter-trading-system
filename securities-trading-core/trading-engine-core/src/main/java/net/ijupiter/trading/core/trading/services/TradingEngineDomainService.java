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
import net.ijupiter.trading.core.trading.entities.TradingEngineEntity;
import net.ijupiter.trading.core.trading.repositories.TradingEngineJpaRepository;

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
    private TradingEngineJpaRepository tradingEngineJpaRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    @Override
    public List<TradingEngineDTO> findAll() {
        List<TradingEngineEntity> entities = tradingEngineJpaRepository.findAll();
        return entities.stream()
                .map(new TradingEngineDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<TradingEngineDTO> findById(Long id) {
        Optional<TradingEngineEntity> entity = tradingEngineJpaRepository.findById(id);
        return entity.map(new TradingEngineDTO()::convertFrom);
    }
    
    @Override
    public TradingEngineDTO save(TradingEngineDTO tradingEngineDTO) {
        TradingEngineEntity entity = new TradingEngineEntity().convertFrom(tradingEngineDTO);
        TradingEngineEntity savedEntity = tradingEngineJpaRepository.save(entity);
        return new TradingEngineDTO().convertFrom(savedEntity);
    }
    
    @Override
    public void deleteById(Long id) {
        if (tradingEngineJpaRepository.existsById(id)) {
            tradingEngineJpaRepository.deleteById(id);
        } else {
            log.warn("尝试删除不存在的交易记录: {}", id);
        }
    }
    
    @Override
    public void delete(TradingEngineDTO entity) {
        if (entity.getId() != null) {
            deleteById(entity.getId());
        }
    }
    
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("不支持批量删除所有交易记录");
    }
    
    @Override
    public TradingEngineDTO saveAndFlush(TradingEngineDTO entity) {
        return save(entity);
    }
    
    @Override
    public List<TradingEngineDTO> saveAll(List<TradingEngineDTO> entities) {
        return entities.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TradingEngineDTO> findAllById(List<Long> ids) {
        List<TradingEngineEntity> entities = tradingEngineJpaRepository.findAllById(ids);
        return entities.stream()
                .map(new TradingEngineDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsById(Long id) {
        return tradingEngineJpaRepository.existsById(id);
    }
    
    @Override
    public long count() {
        return tradingEngineJpaRepository.count();
    }
    
    @Override
    public List<TradingEngineDTO> findByCustomerId(Long customerId) {
        List<TradingEngineEntity> entities = tradingEngineJpaRepository.findByCustomerId(customerId);
        return entities.stream()
                .map(new TradingEngineDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TradingEngineDTO> findByStatus(Integer status) {
        List<TradingEngineEntity> entities = tradingEngineJpaRepository.findByStatus(status);
        return entities.stream()
                .map(new TradingEngineDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TradingEngineDTO> findByTradeDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<TradingEngineEntity> entities = tradingEngineJpaRepository.findByExecuteTimeBetween(startDate, endDate);
        return entities.stream()
                .map(new TradingEngineDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TradingEngineDTO> findBySecurityCode(String securityCode) {
        List<TradingEngineEntity> entities = tradingEngineJpaRepository.findBySecurityCode(securityCode);
        return entities.stream()
                .map(new TradingEngineDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TradingEngineDTO> findByTradeType(Integer tradeType) {
        List<TradingEngineEntity> entities = tradingEngineJpaRepository.findByTradeType(tradeType);
        return entities.stream()
                .map(new TradingEngineDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TradingEngineDTO> findByMarket(Integer market) {
        List<TradingEngineEntity> entities = tradingEngineJpaRepository.findByMarket(market);
        return entities.stream()
                .map(new TradingEngineDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<TradingEngineDTO> findByTradeCode(String tradeCode) {
        TradingEngineEntity entity = tradingEngineJpaRepository.findByTradeCode(tradeCode);
        return entity != null ? Optional.of(new TradingEngineDTO().convertFrom(entity)) : Optional.empty();
    }
    
    @Override
    public List<TradingEngineDTO> findByOrderCode(String orderCode) {
        List<TradingEngineEntity> entities = tradingEngineJpaRepository.findByOrderCode(orderCode);
        return entities.stream()
                .map(new TradingEngineDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public TradingStatistics getTradingStatistics() {
        // 获取总交易数、各状态交易数
        long totalTrades = tradingEngineJpaRepository.countAll();
        long pendingTrades = tradingEngineJpaRepository.countByStatus(1); // 待撮合
        long partialTrades = tradingEngineJpaRepository.countByStatus(2); // 部分成交
        long completedTrades = tradingEngineJpaRepository.countByStatus(3); // 全部成交
        long cancelledTrades = tradingEngineJpaRepository.countByStatus(4); // 已撤销
        
        // 获取总金额和总手续费
        BigDecimal totalAmount = tradingEngineJpaRepository.sumAllAmount();
        BigDecimal totalFee = tradingEngineJpaRepository.sumAllFee();
        
        // 获取买入和卖出交易数
        long buyTrades = tradingEngineJpaRepository.countBuyTrades();
        long sellTrades = tradingEngineJpaRepository.countSellTrades();
        
        // 今日交易(根据成交时间范围查询)
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        
        List<TradingEngineEntity> todayTradeEntities = tradingEngineJpaRepository.findByExecuteTimeBetween(todayStart, todayEnd);
        
        long todayTrades = todayTradeEntities.size();
        
        // 今日交易金额(只统计全部成交的记录)
        BigDecimal todayTradeAmount = todayTradeEntities.stream()
                .filter(t -> t.getStatus() == 3) // 全部成交
                .map(t -> t.getAmount() != null ? t.getAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
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