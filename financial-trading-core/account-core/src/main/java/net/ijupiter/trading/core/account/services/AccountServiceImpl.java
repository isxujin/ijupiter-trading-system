package net.ijupiter.trading.core.account.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.account.commands.CloseAccountCommand;
import net.ijupiter.trading.api.account.commands.CreateAccountCommand;
import net.ijupiter.trading.api.account.commands.UpdateAccountCommand;
import net.ijupiter.trading.api.account.dto.AccountDTO;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.account.enums.AccountType;
import net.ijupiter.trading.api.account.events.AccountClosedEvent;
import net.ijupiter.trading.api.account.events.AccountCreatedEvent;
import net.ijupiter.trading.api.account.events.AccountUpdatedEvent;
import net.ijupiter.trading.api.account.services.AccountService;
import net.ijupiter.trading.core.account.entities.AccountEntity;
import net.ijupiter.trading.core.account.repositories.AccountRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 账户服务实现
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final CommandGateway commandGateway;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public String createAccount(CreateAccountCommand command) {
        log.info("创建账户: {}", command);
        
        // 检查用户是否已有相同类型的账户
        if (hasAccountType(command.getUserId(), command.getAccountType())) {
            throw new IllegalStateException("用户已有相同类型的账户");
        }
        
        // 如果未提供账户ID，则生成一个
        String accountId = command.getAccountId();
        if (accountId == null || accountId.isEmpty()) {
            accountId = UUID.randomUUID().toString().replace("-", "");
        }
        
        // 创建新的命令对象
        CreateAccountCommand createCommand = new CreateAccountCommand(
                accountId,
                command.getUserId(),
                command.getAccountName(),
                command.getAccountType()
        );
        
        try {
            return commandGateway.sendAndWait(createCommand);
        } catch (Exception e) {
            log.error("创建账户失败: {}", e.getMessage(), e);
            throw new RuntimeException("创建账户失败", e);
        }
    }

    @Override
    @Transactional
    public boolean updateAccount(UpdateAccountCommand command) {
        log.info("更新账户: {}", command);
        
        try {
            commandGateway.sendAndWait(command);
            return true;
        } catch (Exception e) {
            log.error("更新账户失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean closeAccount(CloseAccountCommand command) {
        log.info("关闭账户: {}", command);
        
        try {
            commandGateway.sendAndWait(command);
            return true;
        } catch (Exception e) {
            log.error("关闭账户失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public AccountDTO getAccount(String accountId) {
        log.debug("查询账户: {}", accountId);
        
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("账户不存在: " + accountId));
        
        return convertToAccountDTO(account);
    }

    @Override
    public List<AccountDTO> getAccountsByUserId(String userId) {
        log.debug("查询用户账户: {}", userId);
        
        List<AccountEntity> accounts = accountRepository.findByUserId(userId);
        
        return accounts.stream()
                .map(this::convertToAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAccountsByType(String userId, AccountType accountType) {
        log.debug("查询用户特定类型账户: userId={}, type={}", userId, accountType);
        
        List<AccountEntity> accounts = accountRepository.findByUserIdAndAccountType(userId, accountType);
        
        return accounts.stream()
                .map(this::convertToAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAccountsByStatus(String userId, AccountStatus accountStatus) {
        log.debug("查询用户特定状态账户: userId={}, status={}", userId, accountStatus);
        
        List<AccountEntity> accounts = accountRepository.findByUserIdAndAccountStatus(userId, accountStatus);
        
        return accounts.stream()
                .map(this::convertToAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean accountExists(String accountId) {
        log.debug("检查账户是否存在: {}", accountId);
        
        return accountRepository.existsById(accountId);
    }

    @Override
    public boolean hasAccountType(String userId, AccountType accountType) {
        log.debug("检查用户是否已有指定类型的账户: userId={}, type={}", userId, accountType);
        
        return accountRepository.existsByUserIdAndAccountType(userId, accountType);
    }

    @Override
    @Transactional
    public boolean activateAccount(String accountId) {
        log.info("激活账户: {}", accountId);
        
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("账户不存在: " + accountId));
        
        account.setAccountStatus(AccountStatus.NORMAL);
        account.setUpdateTime(java.time.LocalDateTime.now());
        accountRepository.save(account);
        
        return true;
    }

    @Override
    @Transactional
    public boolean freezeAccount(String accountId, String reason) {
        log.info("冻结账户: accountId={}, reason={}", accountId, reason);
        
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("账户不存在: " + accountId));
        
        account.setAccountStatus(AccountStatus.FROZEN);
        account.setUpdateTime(java.time.LocalDateTime.now());
        accountRepository.save(account);
        
        return true;
    }

    @Override
    @Transactional
    public boolean unfreezeAccount(String accountId, String reason) {
        log.info("解冻账户: accountId={}, reason={}", accountId, reason);
        
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("账户不存在: " + accountId));
        
        account.setAccountStatus(AccountStatus.NORMAL);
        account.setUpdateTime(java.time.LocalDateTime.now());
        accountRepository.save(account);
        
        return true;
    }

    /**
     * 处理账户创建事件
     * 
     * @param event 账户创建事件
     */
    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.debug("处理账户创建事件: {}", event);
        
        AccountEntity account = new AccountEntity();
        account.setAccountId(event.getAccountId());
        account.setUserId(event.getUserId());
        account.setAccountName(event.getAccountName());
        account.setAccountType(event.getAccountType());
        account.setAccountStatus(event.getStatus());
        account.setCreateTime(event.getCreateTime());
        account.setUpdateTime(event.getCreateTime());
        
        accountRepository.save(account);
    }

    /**
     * 处理账户更新事件
     * 
     * @param event 账户更新事件
     */
    @EventHandler
    public void on(AccountUpdatedEvent event) {
        log.debug("处理账户更新事件: {}", event);
        
        AccountEntity account = accountRepository.findById(event.getAccountId())
                .orElseThrow(() -> new RuntimeException("账户不存在: " + event.getAccountId()));
        
        account.setAccountName(event.getAccountName());
        account.setUpdateTime(event.getUpdateTime());
        
        accountRepository.save(account);
    }

    /**
     * 处理账户关闭事件
     * 
     * @param event 账户关闭事件
     */
    @EventHandler
    public void on(AccountClosedEvent event) {
        log.debug("处理账户关闭事件: {}", event);
        
        AccountEntity account = accountRepository.findById(event.getAccountId())
                .orElseThrow(() -> new RuntimeException("账户不存在: " + event.getAccountId()));
        
        account.setAccountStatus(AccountStatus.CLOSED);
        account.setCloseTime(event.getCloseTime());
        account.setCloseReason(event.getReason());
        
        accountRepository.save(account);
    }

    /**
     * 转换账户实体为DTO
     * 
     * @param account 账户实体
     * @return 账户DTO
     */
    private AccountDTO convertToAccountDTO(AccountEntity account) {
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(account, dto);
        return dto;
    }
}