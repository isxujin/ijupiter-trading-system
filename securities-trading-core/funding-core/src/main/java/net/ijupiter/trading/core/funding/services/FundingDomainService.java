package net.ijupiter.trading.core.funding.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ijupiter.trading.api.funding.commands.CreateFundingAccountCommand;
import net.ijupiter.trading.api.funding.commands.TransferFundingCommand;
import net.ijupiter.trading.api.funding.commands.FreezeFundingCommand;
import net.ijupiter.trading.api.funding.dtos.FundingAccountDTO;
import net.ijupiter.trading.api.funding.dtos.FundingTransferDTO;
import net.ijupiter.trading.api.funding.dtos.FundingTransactionDTO;
import net.ijupiter.trading.api.funding.services.FundingService;
import net.ijupiter.trading.core.funding.repositories.FundingJpaRepository;
import net.ijupiter.trading.core.funding.repositories.FundingTransactionJpaRepository;
import net.ijupiter.trading.core.funding.repositories.FundingAccountLedgerJpaRepository;
import net.ijupiter.trading.core.funding.entities.FundingAccountEntity;
import net.ijupiter.trading.core.funding.entities.FundingTransactionEntity;
import net.ijupiter.trading.core.funding.entities.FundingAccountLedgerEntity;


import org.axonframework.commandhandling.gateway.CommandGateway;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 资金领域服务实现
 */
@Slf4j
@Service
@Transactional
public class FundingDomainService implements FundingService {
    
    @Autowired
    private FundingJpaRepository fundingJpaRepository;
    
    @Autowired
    private FundingTransactionJpaRepository transactionJpaRepository;
    
    @Autowired
    private FundingAccountLedgerJpaRepository ledgerJpaRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    // ==================== 实现BaseService接口方法 ====================
    
    @Override
    public List<FundingAccountDTO> findAll() {
        List<FundingAccountEntity> entities = fundingJpaRepository.findAll();
        return entities.stream()
                .map(entity -> {
                    FundingAccountDTO dto = new FundingAccountDTO();
                    dto.convertFrom(entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<FundingAccountDTO> findById(Long id) {
        Optional<FundingAccountEntity> entity = fundingJpaRepository.findById(id);
        return entity.map(e -> {
            FundingAccountDTO dto = new FundingAccountDTO();
            dto.convertFrom(e);
            return dto;
        });
    }
    
    @Override
    public Optional<FundingAccountDTO> findByAccountCode(String accountCode) {
        FundingAccountEntity entity = fundingJpaRepository.findByAccountCode(accountCode);
        if (entity == null) {
            return Optional.empty();
        }
        FundingAccountDTO dto = new FundingAccountDTO();
        dto.convertFrom(entity);
        return Optional.of(dto);
    }
    
    @Override
    public List<FundingAccountDTO> findByCustomerId(Long customerId) {
        List<FundingAccountEntity> entities = fundingJpaRepository.findByCustomerId(customerId);
        return entities.stream()
                .map(entity -> {
                    FundingAccountDTO dto = new FundingAccountDTO();
                    dto.convertFrom(entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        fundingJpaRepository.deleteById(id);
    }
    
    @Override
    public void delete(FundingAccountDTO entity) {
        fundingJpaRepository.deleteById(entity.getId());
    }
    
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("不支持批量删除所有资金账户");
    }
    
    @Override
    public FundingAccountDTO save(FundingAccountDTO fundingAccountDTO) {
        FundingAccountEntity entity = new FundingAccountEntity();
        entity.convertFrom(fundingAccountDTO);
        FundingAccountEntity savedEntity = fundingJpaRepository.save(entity);
        
        FundingAccountDTO resultDTO = new FundingAccountDTO();
        resultDTO.convertFrom(savedEntity);
        return resultDTO;
    }
    
    @Override
    public FundingAccountDTO saveAndFlush(FundingAccountDTO entity) {
        return save(entity);
    }
    
    @Override
    public List<FundingAccountDTO> saveAll(List<FundingAccountDTO> entities) {
        List<FundingAccountEntity> fundingAccountEntities = entities.stream()
                .map(dto -> {
                    FundingAccountEntity entity = new FundingAccountEntity();
                    entity.convertFrom(dto);
                    return entity;
                })
                .collect(Collectors.toList());
        
        List<FundingAccountEntity> savedEntities = fundingJpaRepository.saveAll(fundingAccountEntities);
        
        return savedEntities.stream()
                .map(entity -> {
                    FundingAccountDTO dto = new FundingAccountDTO();
                    dto.convertFrom(entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<FundingAccountDTO> findAllById(List<Long> ids) {
        List<FundingAccountEntity> entities = fundingJpaRepository.findAllById(ids);
        return entities.stream()
                .map(entity -> {
                    FundingAccountDTO dto = new FundingAccountDTO();
                    dto.convertFrom(entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsById(Long id) {
        return fundingJpaRepository.existsById(id);
    }
    
    @Override
    public long count() {
        return fundingJpaRepository.count();
    }
    
    // ==================== 实现FundingService特有方法 ====================
    
    public boolean existsByAccountCode(String accountCode) {
        return fundingJpaRepository.findByAccountCode(accountCode) != null;
    }
    
    public List<FundingTransferDTO> findTransfersByCustomerId(Long customerId) {
        // 由于我们删除了FundingTransferEntity，这里返回空列表
        // 实际实现可以根据需要重新定义
        return List.of();
    }
    
    @Override
    public List<FundingTransactionDTO> findTransactionsByAccountCode(String accountCode) {
        List<FundingTransactionEntity> entities = transactionJpaRepository.findByAccountCodeOrderByTransactionTimeDesc(accountCode);
        return entities.stream()
                .map(entity -> {
                    FundingTransactionDTO dto = new FundingTransactionDTO();
                    dto.convertFrom(entity);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public FundingTransferDTO createTransfer(FundingTransferDTO transferDTO) {
        // 由于我们删除了FundingTransferEntity，这里只返回空DTO
        // 实际转账业务应该通过创建两笔资金流水记录来实现
        // 一笔转出，一笔转入
        return new FundingTransferDTO();
    }
    
    @Override
    public boolean freezeAccount(String accountCode, BigDecimal amount, String reason, String operatorId) {
        FundingAccountEntity account = fundingJpaRepository.findByAccountCode(accountCode);
        if (account == null) {
            throw new IllegalArgumentException("账户不存在: " + accountCode);
        }
        
        try {
            account.freezeAmount(amount);
            fundingJpaRepository.save(account);
            
            // 创建冻结交易记录
            FundingTransactionEntity transaction = FundingTransactionEntity.createFreeze(
                    generateTransactionCode(), accountCode, account.getCustomerId(), 
                    account.getCustomerCode(), amount, account.getBalance(), operatorId, reason);
            transactionJpaRepository.save(transaction);
            
            // 更新台账（使用独立的服务处理）
            updateLedgerWithTransaction(transaction);
            
            return true;
        } catch (Exception e) {
            log.error("冻结账户失败: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean unfreezeAccount(String accountCode, BigDecimal amount, String operatorId) {
        FundingAccountEntity account = fundingJpaRepository.findByAccountCode(accountCode);
        if (account == null) {
            throw new IllegalArgumentException("账户不存在: " + accountCode);
        }
        
        try {
            account.unfreezeAmount(amount);
            fundingJpaRepository.save(account);
            
            // 创建解冻交易记录
            FundingTransactionEntity transaction = FundingTransactionEntity.createUnfreeze(
                    generateTransactionCode(), accountCode, account.getCustomerId(), 
                    account.getCustomerCode(), amount, account.getBalance(), operatorId, "解冻资金");
            transactionJpaRepository.save(transaction);
            
            // 更新台账
            updateLedgerWithTransaction(transaction);
            
            return true;
        } catch (Exception e) {
            log.error("解冻账户失败: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public FundingStatistics getFundingStatistics() {
        // 获取账户统计
        long totalAccounts = fundingJpaRepository.countAll();
        long activeAccounts = fundingJpaRepository.countByStatus(1); // 1表示正常状态
        long frozenAccounts = fundingJpaRepository.countByStatus(2); // 2表示冻结状态
        
        // 获取余额统计
        BigDecimal totalBalance = fundingJpaRepository.sumAllBalance();
        BigDecimal totalFrozenAmount = fundingJpaRepository.sumAllFrozenAmount();
        
        // 获取今日转账统计（这里简化处理，实际应该从台账中获取）
        long todayTransfers = 0; // 简化为0
        BigDecimal todayTransferAmount = BigDecimal.ZERO; // 简化为0
        
        return new FundingStatistics(totalAccounts, activeAccounts, frozenAccounts, 
                totalBalance, totalFrozenAmount, todayTransfers, todayTransferAmount);
    }
    
    public FundingTransactionDTO saveTransaction(FundingTransactionDTO transactionDTO) {
        FundingTransactionEntity entity = new FundingTransactionEntity();
        entity.convertFrom(transactionDTO);
        
        // 设置交易时间
        if (entity.getTransactionTime() == null) {
            entity.setTransactionTime(LocalDateTime.now());
        }
        
        // 验证必要字段
        entity.validateRequiredFields();
        entity.validateAmount();
        
        // 计算交易后余额
        if (entity.getBalanceBefore() != null && entity.getBalanceAfter() == null) {
            entity.calculateBalanceAfter(entity.getAmount());
        }
        
        FundingTransactionEntity savedEntity = transactionJpaRepository.save(entity);
        
        // 更新台账
        updateLedgerWithTransaction(savedEntity);
        
        FundingTransactionDTO resultDTO = new FundingTransactionDTO();
        resultDTO.convertFrom(savedEntity);
        return resultDTO;
    }
    
    // ==================== 私有方法 ====================
    
    /**
     * 生成交易流水号
     */
    private String generateTransactionCode() {
        return "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
    
    /**
     * 更新台账
     */
    private void updateLedgerWithTransaction(FundingTransactionEntity transaction) {
        try {
            // 获取或创建台账
            LocalDate transactionDate = transaction.getTransactionTime().toLocalDate();
            FundingAccountLedgerEntity ledger = ledgerJpaRepository.findByAccountCodeAndLedgerDate(
                    transaction.getAccountCode(), transactionDate);
            
            if (ledger == null) {
                // 创建新台账
                ledger = createNewLedger(transaction.getAccountCode(), transactionDate);
            }
            
            // 根据交易类型更新台账
            switch (transaction.getTransactionType()) {
                case FundingTransactionEntity.TRANSACTION_TYPE_DEPOSIT:
                    ledger.addDeposit(transaction.getAmount());
                    break;
                case FundingTransactionEntity.TRANSACTION_TYPE_WITHDRAW:
                    ledger.addWithdraw(transaction.getAmount().negate());
                    break;
                case FundingTransactionEntity.TRANSACTION_TYPE_TRANSFER_IN:
                    ledger.addTransferIn(transaction.getAmount());
                    break;
                case FundingTransactionEntity.TRANSACTION_TYPE_TRANSFER_OUT:
                    ledger.addTransferOut(transaction.getAmount().negate());
                    break;
                case FundingTransactionEntity.TRANSACTION_TYPE_FREEZE:
                    ledger.addFreeze(transaction.getAmount().negate());
                    break;
                case FundingTransactionEntity.TRANSACTION_TYPE_UNFREEZE:
                    ledger.addUnfreeze(transaction.getAmount());
                    break;
                case FundingTransactionEntity.TRANSACTION_TYPE_INTEREST:
                    ledger.addInterest(transaction.getAmount());
                    break;
                case FundingTransactionEntity.TRANSACTION_TYPE_FEE:
                    ledger.addFee(transaction.getAmount().negate());
                    break;
                case FundingTransactionEntity.TRANSACTION_TYPE_REFUND:
                    ledger.addRefund(transaction.getAmount());
                    break;
                default:
                    log.warn("未知的交易类型: {}", transaction.getTransactionType());
            }
            
            ledgerJpaRepository.save(ledger);
        } catch (Exception e) {
            log.warn("更新台账失败: {}", e.getMessage());
        }
    }
    
    /**
     * 创建新台账
     */
    private FundingAccountLedgerEntity createNewLedger(String accountCode, LocalDate ledgerDate) {
        // 获取账户信息
        FundingAccountEntity account = fundingJpaRepository.findByAccountCode(accountCode);
        if (account == null) {
            throw new IllegalArgumentException("账户不存在: " + accountCode);
        }
        
        // 查找前一天的台账
        LocalDate previousDate = ledgerDate.minusDays(1);
        FundingAccountLedgerEntity previousLedger = ledgerJpaRepository.findTopByAccountCodeAndLedgerDateLessThanEqualOrderByLedgerDateDesc(
                accountCode, previousDate);
        
        FundingAccountLedgerEntity newLedger;
        if (previousLedger != null) {
            // 从前一天台账创建
            newLedger = FundingAccountLedgerEntity.createFromPrevious(
                    previousLedger, ledgerDate);
        } else {
            // 创建新台账
            newLedger = FundingAccountLedgerEntity.createNew(
                    accountCode, account.getCustomerId(), account.getCustomerCode(),
                    1, ledgerDate, account.getBalance(), account.getFrozenAmount());
        }
        
        return ledgerJpaRepository.save(newLedger);
    }
    
    // ==================== 命令处理方法 ====================
    
    /**
     * 创建资金账户(通过命令)
     */
    public CompletableFuture<String> createFundingAccount(CreateFundingAccountCommand command) {
        log.info("发送创建资金账户命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 转账资金(通过命令)
     */
    public CompletableFuture<Void> transferFunding(TransferFundingCommand command) {
        log.info("发送转账资金命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 冻结资金(通过命令)
     */
    public CompletableFuture<Void> freezeFunding(FreezeFundingCommand command) {
        log.info("发送冻结资金命令: {}", command);
        return commandGateway.send(command);
    }
}