package net.ijupiter.trading.core.securities.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ijupiter.trading.api.securities.commands.CreateSecuritiesAccountCommand;
import net.ijupiter.trading.api.securities.commands.TransferSecuritiesCommand;
import net.ijupiter.trading.api.securities.dtos.SecuritiesAccountDTO;
import net.ijupiter.trading.api.securities.dtos.SecuritiesPositionDTO;
import net.ijupiter.trading.api.securities.dtos.SecuritiesTransferDTO;
import net.ijupiter.trading.api.securities.services.SecuritiesService;
import net.ijupiter.trading.core.securities.repositories.SecuritiesRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 证券领域服务实现
 */
@Slf4j
@Service
@Transactional
public class SecuritiesDomainService implements SecuritiesService {
    
    @Autowired
    private SecuritiesRepository securitiesRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    @Override
    public List<SecuritiesAccountDTO> findAll() {
        return securitiesRepository.findAllAccounts();
    }
    
    @Override
    public Optional<SecuritiesAccountDTO> findById(Long id) {
        return securitiesRepository.findAccountById(id);
    }
    
    @Override
    public SecuritiesAccountDTO save(SecuritiesAccountDTO securitiesAccountDTO) {
        return securitiesRepository.saveAccount(securitiesAccountDTO);
    }
    
    @Override
    public void deleteById(Long id) {
        securitiesRepository.deleteAccountById(id);
    }
    
    @Override
    public void delete(SecuritiesAccountDTO entity) {
        securitiesRepository.deleteAccountById(entity.getId());
    }
    
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("不支持批量删除所有证券账户");
    }
    
    @Override
    public SecuritiesAccountDTO saveAndFlush(SecuritiesAccountDTO entity) {
        return securitiesRepository.saveAccount(entity);
    }
    
    @Override
    public List<SecuritiesAccountDTO> saveAll(List<SecuritiesAccountDTO> entities) {
        return entities.stream()
                .map(securitiesRepository::saveAccount)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SecuritiesAccountDTO> findAllById(List<Long> ids) {
        return ids.stream()
                .map(securitiesRepository::findAccountById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsById(Long id) {
        return securitiesRepository.findAccountById(id).isPresent();
    }
    
    @Override
    public long count() {
        return securitiesRepository.findAllAccounts().size();
    }
    
    @Override
    public List<SecuritiesAccountDTO> findByCustomerId(Long customerId) {
        return securitiesRepository.findAccountsByCustomerId(customerId);
    }
    
    @Override
    public Optional<SecuritiesAccountDTO> findByAccountCode(String accountCode) {
        return securitiesRepository.findAccountByCode(accountCode);
    }
    
    @Override
    public List<SecuritiesPositionDTO> findPositionsByCustomerId(Long customerId) {
        return securitiesRepository.findPositionsByCustomerId(customerId);
    }
    
    @Override
    public List<SecuritiesPositionDTO> findPositionsByAccountCode(String accountCode) {
        return securitiesRepository.findPositionsByAccountCode(accountCode);
    }
    
    @Override
    public List<SecuritiesTransferDTO> findTransfersByCustomerId(Long customerId) {
        return securitiesRepository.findTransfersByCustomerId(customerId);
    }
    
    @Override
    public SecuritiesTransferDTO createTransfer(SecuritiesTransferDTO transferDTO) {
        // 生成转托管编号
        String transferCode = generateTransferCode();
        transferDTO.setTransferCode(transferCode);
        transferDTO.setStatus(1); // 待处理
        transferDTO.setTransferTime(LocalDateTime.now());
        transferDTO.setCreateTime(LocalDateTime.now());
        
        return securitiesRepository.saveTransfer(transferDTO);
    }
    
    @Override
    public SecuritiesStatistics getSecuritiesStatistics() {
        List<SecuritiesAccountDTO> allAccounts = securitiesRepository.findAllAccounts();
        
        long totalAccounts = allAccounts.size();
        long activeAccounts = allAccounts.stream()
                .filter(a -> a.getStatus() == 1)
                .count();
        long frozenAccounts = allAccounts.stream()
                .filter(a -> a.getStatus() == 2)
                .count();
        
        BigDecimal totalMarketValue = allAccounts.stream()
                .map(a -> a.getTotalMarketValue() != null ? a.getTotalMarketValue() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalAssets = allAccounts.stream()
                .map(a -> a.getTotalAssets() != null ? a.getTotalAssets() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 总持仓数(简化处理，实际应该从持仓表统计)
        long totalPositions = 0;
        
        // 今日转托管(简化处理，实际应该从数据库查询)
        long todayTransfers = 0;
        
        return new SecuritiesStatistics(totalAccounts, activeAccounts, frozenAccounts, 
                                 totalMarketValue, totalAssets, totalPositions, todayTransfers);
    }
    
    /**
     * 创建证券账户(通过命令)
     */
    public CompletableFuture<String> createAccount(CreateSecuritiesAccountCommand command) {
        log.info("发送创建证券账户命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 转托管证券(通过命令)
     */
    public CompletableFuture<Void> transferSecurities(TransferSecuritiesCommand command) {
        log.info("发送证券转托管命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 生成转托管编号
     */
    private String generateTransferCode() {
        // 简单生成规则: TS + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "TS" + suffix + random;
    }
}