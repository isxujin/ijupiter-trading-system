package net.ijupiter.trading.core.settlement.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ijupiter.trading.api.settlement.dtos.SettlementDTO;
import net.ijupiter.trading.api.settlement.models.SettlementStatistics;
import net.ijupiter.trading.api.settlement.services.SettlementService;
import net.ijupiter.trading.api.settlement.commands.CreateSettlementCommand;
import net.ijupiter.trading.api.settlement.commands.ProcessSettlementCommand;
import net.ijupiter.trading.api.settlement.commands.CompleteSettlementCommand;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 清算应用服务实现
 */
@Slf4j
@Service
@Transactional
public class SettlementApplicationService implements SettlementService {
    
    @Autowired
    private SettlementDomainService settlementDomainService;
    
    @Override
    public List<SettlementDTO> findAll() {
        return settlementDomainService.findAll();
    }
    
    @Override
    public Optional<SettlementDTO> findById(Long id) {
        return settlementDomainService.findById(id);
    }
    
    @Override
    public SettlementDTO save(SettlementDTO settlementDTO) {
        return settlementDomainService.save(settlementDTO);
    }
    
    @Override
    public void deleteById(Long id) {
        settlementDomainService.deleteById(id);
    }
    
    @Override
    public void delete(SettlementDTO entity) {
        settlementDomainService.delete(entity);
    }
    
    @Override
    public void deleteAll() {
        settlementDomainService.deleteAll();
    }
    
    @Override
    public SettlementDTO saveAndFlush(SettlementDTO entity) {
        return settlementDomainService.saveAndFlush(entity);
    }
    
    @Override
    public List<SettlementDTO> saveAll(List<SettlementDTO> entities) {
        return settlementDomainService.saveAll(entities);
    }
    
    @Override
    public List<SettlementDTO> findAllById(List<Long> ids) {
        return settlementDomainService.findAllById(ids);
    }
    
    @Override
    public boolean existsById(Long id) {
        return settlementDomainService.existsById(id);
    }
    
    @Override
    public long count() {
        return settlementDomainService.count();
    }
    
    @Override
    public List<SettlementDTO> findBySettlementType(Integer settlementType) {
        return settlementDomainService.findBySettlementType(settlementType);
    }
    
    @Override
    public List<SettlementDTO> findByStatus(Integer status) {
        return settlementDomainService.findByStatus(status);
    }
    
    @Override
    public List<SettlementDTO> findBySettlementDate(LocalDateTime startDate, LocalDateTime endDate) {
        return settlementDomainService.findBySettlementDate(startDate, endDate);
    }
    
    @Override
    public List<SettlementDTO> findByBuyerCustomerId(Long buyerCustomerId) {
        return settlementDomainService.findByBuyerCustomerId(buyerCustomerId);
    }
    
    @Override
    public List<SettlementDTO> findBySellerCustomerId(Long sellerCustomerId) {
        return settlementDomainService.findBySellerCustomerId(sellerCustomerId);
    }
    
    @Override
    public Optional<SettlementDTO> findBySettlementCode(String settlementCode) {
        return settlementDomainService.findBySettlementCode(settlementCode);
    }
    
    @Override
    public SettlementStatistics getSettlementStatistics() {
        return settlementDomainService.getSettlementStatistics();
    }
    
    /**
     * 创建清算
     */
    public CompletableFuture<String> createSettlement(CreateSettlementCommand command) {
        return settlementDomainService.createSettlement(command);
    }
    
    /**
     * 处理清算
     */
    public CompletableFuture<Void> processSettlement(String settlementCode, Long operatorId) {
        ProcessSettlementCommand command = ProcessSettlementCommand.builder()
                .settlementCode(settlementCode)
                .operatorId(operatorId)
                .build();
        return settlementDomainService.processSettlement(command);
    }
    
    /**
     * 完成清算
     */
    public CompletableFuture<Void> completeSettlement(String settlementCode, Long operatorId) {
        CompleteSettlementCommand command = CompleteSettlementCommand.builder()
                .settlementCode(settlementCode)
                .operatorId(operatorId)
                .build();
        return settlementDomainService.completeSettlement(command);
    }
    
    /**
     * 处理批量清算
     */
    public CompletableFuture<Void> processBatchSettlement(List<String> settlementCodes, Long operatorId) {
        log.info("开始批量处理清算: {}", settlementCodes);
        
        // 并行处理多个清算
        List<CompletableFuture<Void>> futures = settlementCodes.stream()
                .map(code -> processSettlement(code, operatorId))
                .collect(java.util.stream.Collectors.toList());
        
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }
}