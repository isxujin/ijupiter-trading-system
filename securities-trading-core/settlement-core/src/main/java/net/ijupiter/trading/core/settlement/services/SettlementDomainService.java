package net.ijupiter.trading.core.settlement.services;

import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.settlement.models.SettlementStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ijupiter.trading.api.settlement.commands.CreateSettlementCommand;
import net.ijupiter.trading.api.settlement.commands.ProcessSettlementCommand;
import net.ijupiter.trading.api.settlement.commands.CompleteSettlementCommand;
import net.ijupiter.trading.api.settlement.dtos.SettlementDTO;
import net.ijupiter.trading.api.settlement.services.SettlementService;
import net.ijupiter.trading.core.settlement.entities.SettlementEntity;
import net.ijupiter.trading.core.settlement.repositories.SettlementJpaRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 清算领域服务实现
 */
@Slf4j
@Service
@Transactional
public class SettlementDomainService implements SettlementService {
    
    @Autowired
    private SettlementJpaRepository settlementJpaRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    @Override
    public List<SettlementDTO> findAll() {
        List<SettlementEntity> entities = settlementJpaRepository.findAll();
        return entities.stream()
                .map(new SettlementDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<SettlementDTO> findById(Long id) {
        Optional<SettlementEntity> entity = settlementJpaRepository.findById(id);
        return entity.map(new SettlementDTO()::convertFrom);
    }
    
    @Override
    public SettlementDTO save(SettlementDTO settlementDTO) {
        SettlementEntity entity = new SettlementEntity().convertFrom(settlementDTO);
        SettlementEntity savedEntity = settlementJpaRepository.save(entity);
        return new SettlementDTO().convertFrom(savedEntity);
    }
    
    @Override
    public void deleteById(Long id) {
        if (settlementJpaRepository.existsById(id)) {
            settlementJpaRepository.deleteById(id);
        } else {
            log.warn("尝试删除不存在的清算记录: {}", id);
        }
    }
    
    @Override
    public void delete(SettlementDTO entity) {
        if (entity.getId() != null) {
            deleteById(entity.getId());
        }
    }
    
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("不支持批量删除所有清算记录");
    }
    
    @Override
    public SettlementDTO saveAndFlush(SettlementDTO entity) {
        return save(entity);
    }
    
    @Override
    public List<SettlementDTO> saveAll(List<SettlementDTO> entities) {
        return entities.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SettlementDTO> findAllById(List<Long> ids) {
        List<SettlementEntity> entities = settlementJpaRepository.findAllById(ids);
        return entities.stream()
                .map(new SettlementDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsById(Long id) {
        return settlementJpaRepository.existsById(id);
    }
    
    @Override
    public long count() {
        return settlementJpaRepository.count();
    }
    
    @Override
    public List<SettlementDTO> findBySettlementType(Integer settlementType) {
        List<SettlementEntity> entities = settlementJpaRepository.findBySettlementType(settlementType);
        return entities.stream()
                .map(new SettlementDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SettlementDTO> findByStatus(Integer status) {
        List<SettlementEntity> entities = settlementJpaRepository.findByStatus(status);
        return entities.stream()
                .map(new SettlementDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SettlementDTO> findBySettlementDate(LocalDateTime startDate, LocalDateTime endDate) {
        List<SettlementEntity> entities = settlementJpaRepository.findBySettlementDateBetween(startDate, endDate);
        return entities.stream()
                .map(new SettlementDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SettlementDTO> findByBuyerCustomerId(Long buyerCustomerId) {
        List<SettlementEntity> entities = settlementJpaRepository.findByBuyerCustomerId(buyerCustomerId);
        return entities.stream()
                .map(new SettlementDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SettlementDTO> findBySellerCustomerId(Long sellerCustomerId) {
        List<SettlementEntity> entities = settlementJpaRepository.findBySellerCustomerId(sellerCustomerId);
        return entities.stream()
                .map(new SettlementDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<SettlementDTO> findBySettlementCode(String settlementCode) {
        SettlementEntity entity = settlementJpaRepository.findBySettlementCode(settlementCode);
        return entity != null ? Optional.of(new SettlementDTO().convertFrom(entity)) : Optional.empty();
    }
    
    @Override
    public SettlementStatistics getSettlementStatistics() {
        // 获取总清算数、各状态清算数
        long totalSettlements = settlementJpaRepository.countAll();
        long pendingSettlements = settlementJpaRepository.countByStatus(1); // 待清算
        long processingSettlements = settlementJpaRepository.countByStatus(2); // 清算中
        long completedSettlements = settlementJpaRepository.countByStatus(3); // 已清算
        long failedSettlements = settlementJpaRepository.countByStatus(4); // 清算失败
        
        // 获取总金额、总手续费、总印花税
        BigDecimal totalAmount = settlementJpaRepository.sumAllAmount();
        BigDecimal totalFee = settlementJpaRepository.sumAllFee();
        BigDecimal totalTax = settlementJpaRepository.sumAllTax();
        
        // 今日清算(根据清算日期范围查询)
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        
        List<SettlementEntity> todaySettlementEntities = settlementJpaRepository.findBySettlementDateBetween(todayStart, todayEnd);
        
        long todaySettlements = todaySettlementEntities.size();
        
        // 今日清算金额(只统计已清算的记录)
        BigDecimal todaySettlementAmount = todaySettlementEntities.stream()
                .filter(s -> s.getStatus() == 3) // 已清算
                .map(s -> s.getAmount() != null ? s.getAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return new SettlementStatistics(totalSettlements, pendingSettlements, processingSettlements,
                completedSettlements, failedSettlements, totalAmount, totalFee, totalTax,
                todaySettlements, todaySettlementAmount);
    }
    
    /**
     * 创建清算记录(通过命令)
     */
    public CompletableFuture<String> createSettlement(CreateSettlementCommand command) {
        log.info("发送创建清算命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 处理清算(通过命令)
     */
    public CompletableFuture<Void> processSettlement(ProcessSettlementCommand command) {
        log.info("发送处理清算命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 完成清算(通过命令)
     */
    public CompletableFuture<Void> completeSettlement(CompleteSettlementCommand command) {
        log.info("发送完成清算命令: {}", command);
        return commandGateway.send(command);
    }
}