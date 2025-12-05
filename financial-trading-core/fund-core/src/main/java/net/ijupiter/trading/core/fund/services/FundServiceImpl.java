package net.ijupiter.trading.core.fund.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.fund.commands.*;
import net.ijupiter.trading.api.fund.events.*;
import net.ijupiter.trading.api.fund.dtos.FundAccountDTO;
import net.ijupiter.trading.api.fund.dtos.FundTransactionDTO;
import net.ijupiter.trading.api.fund.enums.FundAccountStatus;
import net.ijupiter.trading.api.fund.enums.FundTransactionType;
import net.ijupiter.trading.api.fund.services.FundService;
import net.ijupiter.trading.core.fund.entities.FundAccountEntity;
import net.ijupiter.trading.core.fund.entities.FundTransactionEntity;
import net.ijupiter.trading.core.fund.repositories.FundAccountRepository;
import net.ijupiter.trading.core.fund.repositories.FundTransactionRepository;
import net.ijupiter.trading.api.engine.events.TradeExecutedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 资金服务实现
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FundServiceImpl implements FundService {

    private final CommandGateway commandGateway;
    private final FundAccountRepository fundAccountRepository;
    private final FundTransactionRepository fundTransactionRepository;

    @Override
    @Transactional
    public String createFundAccount(CreateFundAccountCommand command) {
        log.info("创建资金账户: {}", command);
        
        // 检查账户是否已有资金账户
        if (hasFundAccount(command.getAccountId())) {
            throw new IllegalStateException("账户已有资金账户");
        }
        
        // 如果未提供资金账户ID，则生成一个
        String fundAccountId = command.getFundAccountId();
        if (fundAccountId == null || fundAccountId.isEmpty()) {
            fundAccountId = UUID.randomUUID().toString().replace("-", "");
        }
        
        // 创建新的命令对象
        CreateFundAccountCommand createCommand = new CreateFundAccountCommand(
                fundAccountId,
                command.getAccountId(),
                command.getInitialBalance()
        );
        
        try {
            return commandGateway.sendAndWait(createCommand);
        } catch (Exception e) {
            log.error("创建资金账户失败: {}", e.getMessage(), e);
            throw new RuntimeException("创建资金账户失败", e);
        }
    }

    @Override
    @Transactional
    public boolean depositFund(DepositFundCommand command) {
        log.info("资金入金: {}", command);
        
        try {
            // 如果未提供交易ID，则生成一个
            String transactionId = command.getTransactionId();
            if (transactionId == null || transactionId.isEmpty()) {
                transactionId = UUID.randomUUID().toString().replace("-", "");
            }
            
            DepositFundCommand depositCommand = new DepositFundCommand(
                    command.getFundAccountId(),
                    transactionId,
                    command.getAmount(),
                    command.getDepositType(),
                    command.getDescription()
            );
            
            commandGateway.sendAndWait(depositCommand);
            return true;
        } catch (Exception e) {
            log.error("资金入金失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean withdrawFund(WithdrawFundCommand command) {
        log.info("资金出金: {}", command);
        
        try {
            // 如果未提供交易ID，则生成一个
            String transactionId = command.getTransactionId();
            if (transactionId == null || transactionId.isEmpty()) {
                transactionId = UUID.randomUUID().toString().replace("-", "");
            }
            
            WithdrawFundCommand withdrawCommand = new WithdrawFundCommand(
                    command.getFundAccountId(),
                    transactionId,
                    command.getAmount(),
                    command.getWithdrawType(),
                    command.getDescription()
            );
            
            commandGateway.sendAndWait(withdrawCommand);
            return true;
        } catch (Exception e) {
            log.error("资金出金失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean freezeFund(FreezeFundCommand command) {
        log.info("冻结资金: {}", command);
        
        try {
            // 如果未提供交易ID，则生成一个
            String transactionId = command.getTransactionId();
            if (transactionId == null || transactionId.isEmpty()) {
                transactionId = UUID.randomUUID().toString().replace("-", "");
            }
            
            FreezeFundCommand freezeCommand = new FreezeFundCommand(
                    command.getFundAccountId(),
                    transactionId,
                    command.getAmount(),
                    command.getDescription()
            );
            
            commandGateway.sendAndWait(freezeCommand);
            return true;
        } catch (Exception e) {
            log.error("冻结资金失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean unfreezeFund(UnfreezeFundCommand command) {
        log.info("解冻资金: {}", command);
        
        try {
            // 如果未提供交易ID，则生成一个
            String transactionId = command.getTransactionId();
            if (transactionId == null || transactionId.isEmpty()) {
                transactionId = UUID.randomUUID().toString().replace("-", "");
            }
            
            UnfreezeFundCommand unfreezeCommand = new UnfreezeFundCommand(
                    command.getFundAccountId(),
                    transactionId,
                    command.getAmount(),
                    command.getDescription()
            );
            
            commandGateway.sendAndWait(unfreezeCommand);
            return true;
        } catch (Exception e) {
            log.error("解冻资金失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public FundAccountDTO getFundAccount(String fundAccountId) {
        log.debug("查询资金账户: {}", fundAccountId);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(fundAccountId)
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + fundAccountId));
        
        return convertToFundAccountDTO(fundAccount);
    }

    @Override
    public FundAccountDTO getFundAccountByAccountId(String accountId) {
        log.debug("根据账户ID查询资金账户: {}", accountId);
        
        FundAccountEntity fundAccount = fundAccountRepository.findByAccountId(accountId);
        if (fundAccount == null) {
            throw new RuntimeException("账户的资金账户不存在: " + accountId);
        }
        
        return convertToFundAccountDTO(fundAccount);
    }

    @Override
    public List<FundTransactionDTO> getTransactionsByTimeRange(String fundAccountId, 
                                                            LocalDateTime startTime, 
                                                            LocalDateTime endTime) {
        log.debug("查询资金账户交易记录: fundAccountId={}, startTime={}, endTime={}", 
                fundAccountId, startTime, endTime);
        
        List<FundTransactionEntity> transactions = fundTransactionRepository
                .findByFundAccountIdAndTransactionTimeBetweenOrderByTransactionTimeDesc(
                        fundAccountId, startTime, endTime);
        
        return transactions.stream()
                .map(this::convertToFundTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FundTransactionDTO> getDepositsByTimeRange(String fundAccountId, 
                                                         LocalDateTime startTime, 
                                                         LocalDateTime endTime) {
        log.debug("查询资金账户入金记录: fundAccountId={}, startTime={}, endTime={}", 
                fundAccountId, startTime, endTime);
        
        List<FundTransactionEntity> deposits = fundTransactionRepository
                .findDepositsByFundAccountIdAndTransactionTimeBetweenOrderByTransactionTimeDesc(
                        fundAccountId, startTime, endTime);
        
        return deposits.stream()
                .map(this::convertToFundTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FundTransactionDTO> getWithdrawalsByTimeRange(String fundAccountId, 
                                                           LocalDateTime startTime, 
                                                           LocalDateTime endTime) {
        log.debug("查询资金账户出金记录: fundAccountId={}, startTime={}, endTime={}", 
                fundAccountId, startTime, endTime);
        
        List<FundTransactionEntity> withdrawals = fundTransactionRepository
                .findWithdrawalsByFundAccountIdAndTransactionTimeBetweenOrderByTransactionTimeDesc(
                        fundAccountId, startTime, endTime);
        
        return withdrawals.stream()
                .map(this::convertToFundTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean fundAccountExists(String fundAccountId) {
        log.debug("检查资金账户是否存在: {}", fundAccountId);
        
        return fundAccountRepository.existsByFundAccountId(fundAccountId);
    }

    @Override
    public boolean hasFundAccount(String accountId) {
        log.debug("检查账户是否已有资金账户: {}", accountId);
        
        return fundAccountRepository.existsByAccountId(accountId);
    }

    @Override
    public boolean hasAvailableBalance(String fundAccountId, BigDecimal amount) {
        log.debug("检查可用余额是否足够: fundAccountId={}, amount={}", fundAccountId, amount);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(fundAccountId)
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + fundAccountId));
        
        BigDecimal availableBalance = fundAccount.getBalance().subtract(fundAccount.getFrozenAmount());
        return availableBalance.compareTo(amount) >= 0;
    }

    @Override
    public BigDecimal getBalance(String fundAccountId) {
        log.debug("查询账户余额: {}", fundAccountId);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(fundAccountId)
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + fundAccountId));
        
        return fundAccount.getBalance();
    }

    @Override
    public BigDecimal getAvailableBalance(String fundAccountId) {
        log.debug("查询可用余额: {}", fundAccountId);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(fundAccountId)
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + fundAccountId));
        
        return fundAccount.getBalance().subtract(fundAccount.getFrozenAmount());
    }

    @Override
    public BigDecimal getFrozenAmount(String fundAccountId) {
        log.debug("查询冻结金额: {}", fundAccountId);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(fundAccountId)
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + fundAccountId));
        
        return fundAccount.getFrozenAmount();
    }

    @Override
    @Transactional
    public boolean freezeAccount(String fundAccountId, String reason) {
        log.info("冻结资金账户: fundAccountId={}, reason={}", fundAccountId, reason);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(fundAccountId)
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + fundAccountId));
        
        fundAccount.setStatus(FundAccountStatus.FROZEN);
        fundAccount.setUpdateTime(LocalDateTime.now());
        fundAccountRepository.save(fundAccount);
        
        return true;
    }

    @Override
    @Transactional
    public boolean unfreezeAccount(String fundAccountId, String reason) {
        log.info("解冻资金账户: fundAccountId={}, reason={}", fundAccountId, reason);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(fundAccountId)
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + fundAccountId));
        
        fundAccount.setStatus(FundAccountStatus.NORMAL);
        fundAccount.setUpdateTime(LocalDateTime.now());
        fundAccountRepository.save(fundAccount);
        
        return true;
    }

    @Override
    @Transactional
    public boolean batchFreezeFunds(List<String> fundAccountIds, BigDecimal amount, String reason) {
        log.info("批量冻结资金: fundAccountIds={}, amount={}, reason={}", fundAccountIds, amount, reason);
        
        boolean success = true;
        for (String fundAccountId : fundAccountIds) {
            try {
                String transactionId = UUID.randomUUID().toString().replace("-", "");
                FreezeFundCommand command = new FreezeFundCommand(
                        fundAccountId, transactionId, amount, reason);
                success &= freezeFund(command);
            } catch (Exception e) {
                log.error("批量冻结资金失败: fundAccountId={}, error={}", fundAccountId, e.getMessage(), e);
                success = false;
            }
        }
        
        return success;
    }

    @Override
    @Transactional
    public boolean batchUnfreezeFunds(List<String> fundAccountIds, BigDecimal amount, String reason) {
        log.info("批量解冻资金: fundAccountIds={}, amount={}, reason={}", fundAccountIds, amount, reason);
        
        boolean success = true;
        for (String fundAccountId : fundAccountIds) {
            try {
                String transactionId = UUID.randomUUID().toString().replace("-", "");
                UnfreezeFundCommand command = new UnfreezeFundCommand(
                        fundAccountId, transactionId, amount, reason);
                success &= unfreezeFund(command);
            } catch (Exception e) {
                log.error("批量解冻资金失败: fundAccountId={}, error={}", fundAccountId, e.getMessage(), e);
                success = false;
            }
        }
        
        return success;
    }

    /**
     * 处理交易执行事件
     * 
     * @param event 交易执行事件
     */
    @EventHandler
    public void on(TradeExecutedEvent event) {
        // @TODO 实现处理交易执行事件逻辑
        // 处理交易执行后的资金变动
        // 包括买方扣款、卖方入账等操作
    }

    /**
     * 处理资金账户创建事件
     * 
     * @param event 资金账户创建事件
     */
    @EventHandler
    public void on(FundAccountCreatedEvent event) {
        log.debug("处理资金账户创建事件: {}", event);
        
        FundAccountEntity fundAccount = new FundAccountEntity();
        fundAccount.setFundAccountId(event.getFundAccountId());
        fundAccount.setAccountId(event.getAccountId());
        fundAccount.setBalance(event.getBalance());
        fundAccount.setFrozenAmount(event.getFrozenAmount());
        fundAccount.setStatus(event.getStatus());
        fundAccount.setCreateTime(event.getCreateTime());
        fundAccount.setUpdateTime(event.getCreateTime());
        
        fundAccountRepository.save(fundAccount);
    }

    /**
     * 处理资金入金事件
     * 
     * @param event 资金入金事件
     */
    @EventHandler
    public void on(FundDepositedEvent event) {
        log.debug("处理资金入金事件: {}", event);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(event.getFundAccountId())
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + event.getFundAccountId()));
        
        fundAccount.setBalance(event.getBalance());
        fundAccount.setUpdateTime(LocalDateTime.now());
        fundAccountRepository.save(fundAccount);
        
        // 保存交易记录
        saveTransaction(event, FundTransactionType.DEPOSIT);
    }

    /**
     * 处理资金出金事件
     * 
     * @param event 资金出金事件
     */
    @EventHandler
    public void on(FundWithdrawnEvent event) {
        log.debug("处理资金出金事件: {}", event);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(event.getFundAccountId())
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + event.getFundAccountId()));
        
        fundAccount.setBalance(event.getBalance());
        fundAccount.setUpdateTime(LocalDateTime.now());
        fundAccountRepository.save(fundAccount);
        
        // 保存交易记录
        saveTransaction(event, FundTransactionType.WITHDRAW);
    }

    /**
     * 处理资金冻结事件
     * 
     * @param event 资金冻结事件
     */
    @EventHandler
    public void on(FundFrozenEvent event) {
        log.debug("处理资金冻结事件: {}", event);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(event.getFundAccountId())
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + event.getFundAccountId()));
        
        fundAccount.setFrozenAmount(event.getFrozenAmount());
        fundAccount.setUpdateTime(LocalDateTime.now());
        fundAccountRepository.save(fundAccount);
        
        // 保存交易记录
        saveTransaction(event, FundTransactionType.FREEZE);
    }

    /**
     * 处理资金解冻事件
     * 
     * @param event 资金解冻事件
     */
    @EventHandler
    public void on(FundUnfrozenEvent event) {
        log.debug("处理资金解冻事件: {}", event);
        
        FundAccountEntity fundAccount = fundAccountRepository.findById(event.getFundAccountId())
                .orElseThrow(() -> new RuntimeException("资金账户不存在: " + event.getFundAccountId()));
        
        fundAccount.setFrozenAmount(event.getFrozenAmount());
        fundAccount.setUpdateTime(LocalDateTime.now());
        fundAccountRepository.save(fundAccount);
        
        // 保存交易记录
        saveTransaction(event, FundTransactionType.UNFREEZE);
    }

    /**
     * 保存交易记录
     * 
     * @param event 事件
     * @param transactionType 交易类型
     */
    private void saveTransaction(Object event, FundTransactionType transactionType) {
        FundTransactionEntity transaction = new FundTransactionEntity();
        
        // 根据事件类型设置交易记录
        if (event instanceof FundDepositedEvent) {
            FundDepositedEvent depositEvent = (FundDepositedEvent) event;
            transaction.setTransactionId(depositEvent.getTransactionId());
            transaction.setFundAccountId(depositEvent.getFundAccountId());
            transaction.setAmount(depositEvent.getAmount());
            transaction.setBalance(depositEvent.getBalance());
            transaction.setDepositType(depositEvent.getDepositType());
            transaction.setDescription(depositEvent.getDescription());
            transaction.setTransactionTime(depositEvent.getTransactionTime());
            
            // 查询资金账户获取账户ID和冻结金额
            FundAccountEntity fundAccount = fundAccountRepository.findById(depositEvent.getFundAccountId())
                    .orElseThrow(() -> new RuntimeException("资金账户不存在: " + depositEvent.getFundAccountId()));
            transaction.setAccountId(fundAccount.getAccountId());
            transaction.setFrozenAmount(fundAccount.getFrozenAmount());
        } else if (event instanceof FundWithdrawnEvent) {
            FundWithdrawnEvent withdrawEvent = (FundWithdrawnEvent) event;
            transaction.setTransactionId(withdrawEvent.getTransactionId());
            transaction.setFundAccountId(withdrawEvent.getFundAccountId());
            transaction.setAmount(withdrawEvent.getAmount());
            transaction.setBalance(withdrawEvent.getBalance());
            transaction.setWithdrawType(withdrawEvent.getWithdrawType());
            transaction.setDescription(withdrawEvent.getDescription());
            transaction.setTransactionTime(withdrawEvent.getTransactionTime());
            
            // 查询资金账户获取账户ID和冻结金额
            FundAccountEntity fundAccount = fundAccountRepository.findById(withdrawEvent.getFundAccountId())
                    .orElseThrow(() -> new RuntimeException("资金账户不存在: " + withdrawEvent.getFundAccountId()));
            transaction.setAccountId(fundAccount.getAccountId());
            transaction.setFrozenAmount(fundAccount.getFrozenAmount());
        } else if (event instanceof FundFrozenEvent) {
            FundFrozenEvent freezeEvent = (FundFrozenEvent) event;
            transaction.setTransactionId(freezeEvent.getTransactionId());
            transaction.setFundAccountId(freezeEvent.getFundAccountId());
            transaction.setAmount(freezeEvent.getAmount());
            transaction.setBalance(freezeEvent.getBalance());
            transaction.setFrozenAmount(freezeEvent.getFrozenAmount());
            transaction.setDescription(freezeEvent.getDescription());
            transaction.setTransactionTime(freezeEvent.getTransactionTime());
            
            // 查询资金账户获取账户ID
            FundAccountEntity fundAccount = fundAccountRepository.findById(freezeEvent.getFundAccountId())
                    .orElseThrow(() -> new RuntimeException("资金账户不存在: " + freezeEvent.getFundAccountId()));
            transaction.setAccountId(fundAccount.getAccountId());
        } else if (event instanceof FundUnfrozenEvent) {
            FundUnfrozenEvent unfreezeEvent = (FundUnfrozenEvent) event;
            transaction.setTransactionId(unfreezeEvent.getTransactionId());
            transaction.setFundAccountId(unfreezeEvent.getFundAccountId());
            transaction.setAmount(unfreezeEvent.getAmount());
            transaction.setBalance(unfreezeEvent.getBalance());
            transaction.setFrozenAmount(unfreezeEvent.getFrozenAmount());
            transaction.setDescription(unfreezeEvent.getDescription());
            transaction.setTransactionTime(unfreezeEvent.getTransactionTime());
            
            // 查询资金账户获取账户ID
            FundAccountEntity fundAccount = fundAccountRepository.findById(unfreezeEvent.getFundAccountId())
                    .orElseThrow(() -> new RuntimeException("资金账户不存在: " + unfreezeEvent.getFundAccountId()));
            transaction.setAccountId(fundAccount.getAccountId());
        }
        
        // 设置交易类型
        transaction.setTransactionType(transactionType);
        
        fundTransactionRepository.save(transaction);
    }

    /**
     * 转换资金账户实体为DTO
     * 
     * @param fundAccount 资金账户实体
     * @return 资金账户DTO
     */
    private FundAccountDTO convertToFundAccountDTO(FundAccountEntity fundAccount) {
        FundAccountDTO dto = new FundAccountDTO();
        BeanUtils.copyProperties(fundAccount, dto);
        
        // 计算可用余额
        BigDecimal balance = fundAccount.getBalance() != null ? fundAccount.getBalance() : BigDecimal.ZERO;
        BigDecimal frozenAmount = fundAccount.getFrozenAmount() != null ? fundAccount.getFrozenAmount() : BigDecimal.ZERO;
        dto.setAvailableBalance(balance.subtract(frozenAmount));
        
        return dto;
    }

    /**
     * 转换交易实体为DTO
     * 
     * @param transaction 交易实体
     * @return 交易DTO
     */
    private FundTransactionDTO convertToFundTransactionDTO(FundTransactionEntity transaction) {
        FundTransactionDTO dto = new FundTransactionDTO();
        BeanUtils.copyProperties(transaction, dto);
        return dto;
    }
}