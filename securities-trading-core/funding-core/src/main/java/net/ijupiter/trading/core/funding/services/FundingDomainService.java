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
import net.ijupiter.trading.core.funding.repositories.FundingRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private FundingRepository fundingRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    @Override
    public List<FundingAccountDTO> findAll() {
        return fundingRepository.findAllAccounts();
    }
    
    @Override
    public Optional<FundingAccountDTO> findById(Long id) {
        return fundingRepository.findAccountById(id);
    }
    
    @Override
    public FundingAccountDTO save(FundingAccountDTO fundingAccountDTO) {
        return fundingRepository.saveAccount(fundingAccountDTO);
    }
    
    @Override
    public void deleteById(Long id) {
        fundingRepository.deleteAccountById(id);
    }
    
    @Override
    public void delete(FundingAccountDTO entity) {
        fundingRepository.deleteAccountById(entity.getId());
    }
    
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("不支持批量删除所有资金账户");
    }
    
    @Override
    public FundingAccountDTO saveAndFlush(FundingAccountDTO entity) {
        return fundingRepository.saveAccount(entity);
    }
    
    @Override
    public List<FundingAccountDTO> saveAll(List<FundingAccountDTO> entities) {
        return entities.stream()
                .map(fundingRepository::saveAccount)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<FundingAccountDTO> findAllById(List<Long> ids) {
        return ids.stream()
                .map(fundingRepository::findAccountById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsById(Long id) {
        return fundingRepository.findAccountById(id).isPresent();
    }
    
    @Override
    public long count() {
        return fundingRepository.findAllAccounts().size();
    }
    
    @Override
    public List<FundingAccountDTO> findByCustomerId(Long customerId) {
        return fundingRepository.findAccountsByCustomerId(customerId);
    }
    
    @Override
    public Optional<FundingAccountDTO> findByAccountCode(String accountCode) {
        return fundingRepository.findAccountByCode(accountCode);
    }
    
    @Override
    public List<FundingTransferDTO> findTransfersByCustomerId(Long customerId) {
        return fundingRepository.findTransfersByCustomerId(customerId);
    }
    
    @Override
    public List<FundingTransactionDTO> findTransactionsByAccountCode(String accountCode) {
        return fundingRepository.findTransactionsByAccountCode(accountCode);
    }
    
    @Override
    public FundingTransferDTO createTransfer(FundingTransferDTO transferDTO) {
        // 生成转账编号
        String transferCode = generateTransferCode();
        transferDTO.setTransferCode(transferCode);
        transferDTO.setStatus(1); // 待处理
        transferDTO.setTransferTime(LocalDateTime.now());
        transferDTO.setCreateTime(LocalDateTime.now());
        
        return fundingRepository.saveTransfer(transferDTO);
    }
    
    @Override
    public boolean freezeAccount(String accountCode, BigDecimal amount, String reason, String operatorId) {
        Optional<FundingAccountDTO> accountOpt = fundingRepository.findAccountByCode(accountCode);
        if (!accountOpt.isPresent()) {
            return false;
        }
        
        // 创建资金冻结流水记录
        FundingTransactionDTO transaction = FundingTransactionDTO.builder()
                .transactionCode(generateTransactionCode())
                .accountCode(accountCode)
                .customerId(accountOpt.get().getCustomerId())
                .customerCode(accountOpt.get().getCustomerCode())
                .transactionType(5) // 冻结
                .amount(amount.negate()) // 冻结是负数
                .balanceBefore(accountOpt.get().getBalance())
                .balanceAfter(accountOpt.get().getBalance().subtract(amount))
                .transactionTime(LocalDateTime.now())
                .operatorId(operatorId)
                .remark(reason)
                .createTime(LocalDateTime.now())
                .build();
        
        fundingRepository.saveTransaction(transaction);
        
        // 更新账户
        FundingAccountDTO account = accountOpt.get();
        account.setFrozenAmount(account.getFrozenAmount().add(amount));
        account.setAvailableBalance(account.getBalance().subtract(account.getFrozenAmount()));
        fundingRepository.updateAccount(account);
        
        return true;
    }
    
    @Override
    public boolean unfreezeAccount(String accountCode, BigDecimal amount, String operatorId) {
        Optional<FundingAccountDTO> accountOpt = fundingRepository.findAccountByCode(accountCode);
        if (!accountOpt.isPresent()) {
            return false;
        }
        
        // 创建资金解冻流水记录
        FundingTransactionDTO transaction = FundingTransactionDTO.builder()
                .transactionCode(generateTransactionCode())
                .accountCode(accountCode)
                .customerId(accountOpt.get().getCustomerId())
                .customerCode(accountOpt.get().getCustomerCode())
                .transactionType(6) // 解冻
                .amount(amount) // 解冻是正数
                .balanceBefore(accountOpt.get().getBalance())
                .balanceAfter(accountOpt.get().getBalance())
                .transactionTime(LocalDateTime.now())
                .operatorId(operatorId)
                .remark("资金解冻")
                .createTime(LocalDateTime.now())
                .build();
        
        fundingRepository.saveTransaction(transaction);
        
        // 更新账户
        FundingAccountDTO account = accountOpt.get();
        account.setFrozenAmount(account.getFrozenAmount().subtract(amount));
        account.setAvailableBalance(account.getBalance().subtract(account.getFrozenAmount()));
        fundingRepository.updateAccount(account);
        
        return true;
    }
    
    @Override
    public FundingStatistics getFundingStatistics() {
        List<FundingAccountDTO> allAccounts = fundingRepository.findAllAccounts();
        
        long totalAccounts = allAccounts.size();
        long activeAccounts = allAccounts.stream()
                .filter(a -> a.getStatus() == 1)
                .count();
        long frozenAccounts = allAccounts.stream()
                .filter(a -> a.getStatus() == 2)
                .count();
        
        BigDecimal totalBalance = allAccounts.stream()
                .map(a -> a.getBalance() != null ? a.getBalance() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalFrozenAmount = allAccounts.stream()
                .map(a -> a.getFrozenAmount() != null ? a.getFrozenAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 今日转账(简化处理，实际应该从数据库查询)
        long todayTransfers = 0;
        BigDecimal todayTransferAmount = BigDecimal.ZERO;
        
        return new FundingStatistics(totalAccounts, activeAccounts, frozenAccounts, 
                                totalBalance, totalFrozenAmount, todayTransfers, todayTransferAmount);
    }
    
    /**
     * 创建资金账户(通过命令)
     */
    public CompletableFuture<String> createAccount(CreateFundingAccountCommand command) {
        log.info("发送创建资金账户命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 转账资金(通过命令)
     */
    public CompletableFuture<Void> transferFunding(TransferFundingCommand command) {
        log.info("发送资金转账命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 冻结资金(通过命令)
     */
    public CompletableFuture<Void> freezeFunding(FreezeFundingCommand command) {
        log.info("发送冻结资金命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 生成转账编号
     */
    private String generateTransferCode() {
        // 简单生成规则: TF + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "TF" + suffix + random;
    }
    
    /**
     * 生成流水编号
     */
    private String generateTransactionCode() {
        // 简单生成规则: TX + 时间戳后8位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "TX" + suffix + random;
    }
}