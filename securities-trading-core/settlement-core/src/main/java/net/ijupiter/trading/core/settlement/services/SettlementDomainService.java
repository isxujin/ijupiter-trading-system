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
import net.ijupiter.trading.core.settlement.repositories.SettlementRepository;

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
    private SettlementRepository settlementRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    @Override
    public List<SettlementDTO> findAll() {
        return settlementRepository.findAllSettlements();
    }
    
    @Override
    public Optional<SettlementDTO> findById(Long id) {
        return settlementRepository.findSettlementById(id);
    }
    
    @Override
    public SettlementDTO save(SettlementDTO settlementDTO) {
        return settlementRepository.saveSettlement(settlementDTO);
    }
    
    @Override
    public void deleteById(Long id) {
        settlementRepository.deleteSettlementById(id);
    }
    
    @Override
    public void delete(SettlementDTO entity) {
        settlementRepository.deleteSettlementById(entity.getId());
    }
    
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("不支持批量删除所有清算记录");
    }
    
    @Override
    public SettlementDTO saveAndFlush(SettlementDTO entity) {
        return settlementRepository.saveSettlement(entity);
    }
    
    @Override
    public List<SettlementDTO> saveAll(List<SettlementDTO> entities) {
        return entities.stream()
                .map(settlementRepository::saveSettlement)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SettlementDTO> findAllById(List<Long> ids) {
        return ids.stream()
                .map(settlementRepository::findSettlementById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsById(Long id) {
        return settlementRepository.findSettlementById(id).isPresent();
    }
    
    @Override
    public long count() {
        return settlementRepository.findAllSettlements().size();
    }
    
    @Override
    public List<SettlementDTO> findBySettlementType(Integer settlementType) {
        return settlementRepository.findSettlementsByType(settlementType);
    }
    
    @Override
    public List<SettlementDTO> findByStatus(Integer status) {
        return settlementRepository.findSettlementsByStatus(status);
    }
    
    @Override
    public List<SettlementDTO> findBySettlementDate(LocalDateTime startDate, LocalDateTime endDate) {
        return settlementRepository.findSettlementsByDateRange(startDate, endDate);
    }
    
    @Override
    public List<SettlementDTO> findByBuyerCustomerId(Long buyerCustomerId) {
        return settlementRepository.findSettlementsByBuyerCustomerId(buyerCustomerId);
    }
    
    @Override
    public List<SettlementDTO> findBySellerCustomerId(Long sellerCustomerId) {
        return settlementRepository.findSettlementsBySellerCustomerId(sellerCustomerId);
    }
    
    @Override
    public Optional<SettlementDTO> findBySettlementCode(String settlementCode) {
        return settlementRepository.findSettlementByCode(settlementCode);
    }
    
    @Override
    public SettlementStatistics getSettlementStatistics() {
        List<SettlementDTO> allSettlements = settlementRepository.findAllSettlements();
        
        long totalSettlements = allSettlements.size();
        long pendingSettlements = allSettlements.stream()
                .filter(s -> s.getStatus() == 1)
                .count();
        long processingSettlements = allSettlements.stream()
                .filter(s -> s.getStatus() == 2)
                .count();
        long completedSettlements = allSettlements.stream()
                .filter(s -> s.getStatus() == 3)
                .count();
        long failedSettlements = allSettlements.stream()
                .filter(s -> s.getStatus() == 4)
                .count();
        
        BigDecimal totalAmount = allSettlements.stream()
                .map(s -> s.getAmount() != null ? s.getAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalFee = allSettlements.stream()
                .map(s -> s.getFee() != null ? s.getFee() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalTax = allSettlements.stream()
                .map(s -> s.getTax() != null ? s.getTax() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 今日清算(简化处理，实际应该从数据库查询)
        long todaySettlements = 0;
        BigDecimal todaySettlementAmount = BigDecimal.ZERO;
        
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