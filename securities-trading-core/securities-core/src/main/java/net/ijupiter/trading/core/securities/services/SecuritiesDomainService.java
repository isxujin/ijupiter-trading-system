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
import net.ijupiter.trading.core.securities.entities.SecuritiesAccountEntity;
import net.ijupiter.trading.core.securities.entities.SecuritiesPositionEntity;
import net.ijupiter.trading.core.securities.entities.SecuritiesTransactionEntity;
import net.ijupiter.trading.core.securities.repositories.SecuritiesJpaRepository;
import net.ijupiter.trading.core.securities.repositories.SecuritiesPositionJpaRepository;
import net.ijupiter.trading.core.securities.repositories.SecuritiesTransactionJpaRepository;

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
    private SecuritiesJpaRepository securitiesJpaRepository;
    
    @Autowired
    private SecuritiesPositionJpaRepository securitiesPositionJpaRepository;
    
    @Autowired
    private SecuritiesTransactionJpaRepository securitiesTransactionJpaRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    @Override
    public List<SecuritiesAccountDTO> findAll() {
        List<SecuritiesAccountEntity> entities = securitiesJpaRepository.findAll();
        return entities.stream()
                .map(new SecuritiesAccountDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<SecuritiesAccountDTO> findById(Long id) {
        Optional<SecuritiesAccountEntity> entity = securitiesJpaRepository.findById(id);
        return entity.map(new SecuritiesAccountDTO()::convertFrom);
    }
    
    @Override
    public SecuritiesAccountDTO save(SecuritiesAccountDTO securitiesAccountDTO) {
        SecuritiesAccountEntity entity = new SecuritiesAccountEntity().convertFrom(securitiesAccountDTO);
        SecuritiesAccountEntity savedEntity = securitiesJpaRepository.save(entity);
        return new SecuritiesAccountDTO().convertFrom(savedEntity);
    }
    
    @Override
    public void deleteById(Long id) {
        if (securitiesJpaRepository.existsById(id)) {
            securitiesJpaRepository.deleteById(id);
        } else {
            log.warn("尝试删除不存在的证券账户: {}", id);
        }
    }
    
    @Override
    public void delete(SecuritiesAccountDTO entity) {
        if (entity.getId() != null) {
            deleteById(entity.getId());
        }
    }
    
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("不支持批量删除所有证券账户");
    }
    
    @Override
    public SecuritiesAccountDTO saveAndFlush(SecuritiesAccountDTO entity) {
        return save(entity);
    }
    
    @Override
    public List<SecuritiesAccountDTO> saveAll(List<SecuritiesAccountDTO> entities) {
        return entities.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SecuritiesAccountDTO> findAllById(List<Long> ids) {
        List<SecuritiesAccountEntity> entities = securitiesJpaRepository.findAllById(ids);
        return entities.stream()
                .map(new SecuritiesAccountDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsById(Long id) {
        return securitiesJpaRepository.existsById(id);
    }
    
    @Override
    public long count() {
        return securitiesJpaRepository.count();
    }
    
    @Override
    public List<SecuritiesAccountDTO> findByCustomerId(Long customerId) {
        List<SecuritiesAccountEntity> entities = securitiesJpaRepository.findByCustomerId(customerId);
        return entities.stream()
                .map(new SecuritiesAccountDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<SecuritiesAccountDTO> findByAccountCode(String accountCode) {
        SecuritiesAccountEntity entity = securitiesJpaRepository.findByAccountCode(accountCode);
        return entity != null ? Optional.ofNullable(new SecuritiesAccountDTO().convertFrom(entity)) : Optional.empty();
    }
    
    @Override
    public List<SecuritiesPositionDTO> findPositionsByCustomerId(Long customerId) {
        List<SecuritiesPositionEntity> entities = securitiesPositionJpaRepository.findByCustomerId(customerId);
        return entities.stream()
                .map(new SecuritiesPositionDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SecuritiesPositionDTO> findPositionsByAccountCode(String accountCode) {
        List<SecuritiesPositionEntity> entities = securitiesPositionJpaRepository.findByAccountCode(accountCode);
        return entities.stream()
                .map(new SecuritiesPositionDTO()::convertFrom)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SecuritiesTransferDTO> findTransfersByCustomerId(Long customerId) {
        // 查询客户的所有交易记录
        List<SecuritiesTransactionEntity> customerTransactions = securitiesTransactionJpaRepository.findByCustomerId(customerId);
        
        // 筛选出交易类型为转托管(3:转入,4:转出)的交易记录
        List<SecuritiesTransactionEntity> transferTransactions = customerTransactions.stream()
                .filter(entity -> entity.getTransactionType() == 3 || entity.getTransactionType() == 4)
                .collect(Collectors.toList());
        
        return transferTransactions.stream()
                .map(entity -> {
                    SecuritiesTransferDTO dto = new SecuritiesTransferDTO();
                    dto.setId(entity.getId());
                    dto.setTransferCode(entity.getTransactionCode());
                    dto.setFromCustomerId(entity.getCustomerId());
                    dto.setFromCustomerCode(entity.getCustomerCode());
                    dto.setFromAccountCode(entity.getAccountCode());
                    dto.setToBrokerId(entity.getToBrokerId());
                    dto.setToBrokerName(entity.getToBrokerName());
                    dto.setSecurityCode(entity.getSecurityCode());
                    dto.setSecurityName(entity.getSecurityName());
                    dto.setQuantity(entity.getQuantity());
                    dto.setTransferType(entity.getTransactionType() == 3 ? 2 : 1); // 3:转入->2, 4:转出->1
                    dto.setStatus(entity.getStatus());
                    dto.setTransferTime(entity.getTransactionTime());
                    dto.setOperatorId(entity.getOperatorId());
                    dto.setCreateTime(entity.getCreateTime());
                    dto.setUpdateTime(entity.getUpdateTime());
                    dto.setRemark(entity.getRemark());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public SecuritiesTransferDTO createTransfer(SecuritiesTransferDTO transferDTO) {
        // 生成交易编号
        String transactionCode = generateTransactionCode();
        
        // 创建证券交易实体
        SecuritiesTransactionEntity entity = SecuritiesTransactionEntity.builder()
                .transactionCode(transactionCode)
                .accountCode(transferDTO.getFromAccountCode())
                .customerId(transferDTO.getFromCustomerId())
                .customerCode(transferDTO.getFromCustomerCode())
                .securityCode(transferDTO.getSecurityCode())
                .securityName(transferDTO.getSecurityName())
                .transactionType(transferDTO.getTransferType() == 2 ? 3 : 4) // 2:转入->3, 1:转出->4
                .quantity(transferDTO.getQuantity())
                .toBrokerId(transferDTO.getToBrokerId())
                .toBrokerName(transferDTO.getToBrokerName())
                .transactionTime(transferDTO.getTransferTime() != null ? transferDTO.getTransferTime() : LocalDateTime.now())
                .status(1) // 待处理
                .operatorId(transferDTO.getOperatorId())
                .remark(transferDTO.getRemark())
                .build();
        
        // 计算交易金额
        entity.calculateAmount();
        
        SecuritiesTransactionEntity savedEntity = securitiesTransactionJpaRepository.save(entity);
        
        // 转换为DTO返回
        SecuritiesTransferDTO result = new SecuritiesTransferDTO();
        result.setId(savedEntity.getId());
        result.setTransferCode(savedEntity.getTransactionCode());
        result.setFromCustomerId(savedEntity.getCustomerId());
        result.setFromCustomerCode(savedEntity.getCustomerCode());
        result.setFromAccountCode(savedEntity.getAccountCode());
        result.setToBrokerId(savedEntity.getToBrokerId());
        result.setToBrokerName(savedEntity.getToBrokerName());
        result.setSecurityCode(savedEntity.getSecurityCode());
        result.setSecurityName(savedEntity.getSecurityName());
        result.setQuantity(savedEntity.getQuantity());
        result.setTransferType(savedEntity.getTransactionType() == 3 ? 2 : 1); // 3:转入->2, 4:转出->1
        result.setStatus(savedEntity.getStatus());
        result.setTransferTime(savedEntity.getTransactionTime());
        result.setOperatorId(savedEntity.getOperatorId());
        result.setCreateTime(savedEntity.getCreateTime());
        result.setUpdateTime(savedEntity.getUpdateTime());
        result.setRemark(savedEntity.getRemark());
        
        return result;
    }
    
    @Override
    public SecuritiesStatistics getSecuritiesStatistics() {
        // 获取总账户数、活跃账户数、冻结账户数
        long totalAccounts = securitiesJpaRepository.countAll();
        long activeAccounts = securitiesJpaRepository.countByStatus(1);
        long frozenAccounts = securitiesJpaRepository.countByStatus(2);
        
        // 获取总市值
        BigDecimal totalMarketValue = securitiesPositionJpaRepository.sumAllMarketValue();
        
        // 总资产(证券市值+资金余额)，这里简化处理，只计算证券市值
        BigDecimal totalAssets = totalMarketValue;
        
        // 总持仓数
        long totalPositions = securitiesPositionJpaRepository.countAll();
        
        // 今日转托管数(交易类型为3:转入,4:转出，状态为2:已处理的记录)
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        
        List<SecuritiesTransactionEntity> todayTransferIns = securitiesTransactionJpaRepository.findByTransactionType(3); // 转入
        List<SecuritiesTransactionEntity> todayTransferOuts = securitiesTransactionJpaRepository.findByTransactionType(4); // 转出
        
        List<SecuritiesTransactionEntity> todayTransfers = new java.util.ArrayList<>();
        todayTransfers.addAll(todayTransferIns);
        todayTransfers.addAll(todayTransferOuts);
        
        long todayTransferCount = todayTransfers.stream()
                .filter(t -> t.getStatus() == 2 && // 已处理
                           t.getTransactionTime() != null && 
                           t.getTransactionTime().isAfter(todayStart) && 
                           t.getTransactionTime().isBefore(todayEnd))
                .count();
        
        return new SecuritiesStatistics(totalAccounts, activeAccounts, frozenAccounts, 
                                 totalMarketValue, totalAssets, totalPositions, todayTransferCount);
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
     * 生成交易编号
     */
    private String generateTransactionCode() {
        // 简单生成规则: TS + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "TS" + suffix + random;
    }
}