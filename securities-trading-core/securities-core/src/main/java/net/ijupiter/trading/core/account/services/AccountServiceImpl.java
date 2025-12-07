package net.ijupiter.trading.core.account.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.account.commands.CloseAccountCommand;
import net.ijupiter.trading.api.account.commands.CreateAccountCommand;
import net.ijupiter.trading.api.account.commands.UpdateAccountCommand;
import net.ijupiter.trading.api.account.dtos.AccountDTO;
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
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        return new AccountDTO().convertFrom(account);
    }

    @Override
    public List<AccountDTO> getAccountsByUserId(String userId) {
        log.debug("查询用户账户: {}", userId);
        
        List<AccountEntity> accounts = accountRepository.findByUserId(userId);
        
        return accounts.stream()
                .map(entity->new AccountDTO().convertFrom(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAccountsByType(String userId, AccountType accountType) {
        log.debug("查询用户特定类型账户: userId={}, type={}", userId, accountType);
        
        List<AccountEntity> accounts = accountRepository.findByUserIdAndAccountType(userId, accountType);
        
        return accounts.stream()
                .map(entity->new AccountDTO().convertFrom(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAccountsByStatus(String userId, AccountStatus accountStatus) {
        log.debug("查询用户特定状态账户: userId={}, status={}", userId, accountStatus);
        
        List<AccountEntity> accounts = accountRepository.findByUserIdAndAccountStatus(userId, accountStatus);
        
        return accounts.stream()
                .map(entity->new AccountDTO().convertFrom(entity))
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

    // ==================== BaseService接口实现 ====================

    @Override
    @Transactional
    public AccountDTO save(AccountDTO dto) {
        Assert.notNull(dto, "保存失败：账户DTO不能为空");
        log.debug("保存账户（延迟刷库），DTO核心属性：{}", dto.getId() + "-" + dto.getAccountName());

        try {
            // 显式指定泛型<AccountEntity>，解决Entity转换推断问题
            AccountEntity entity = new AccountEntity().convertFrom(dto);
            AccountEntity savedEntity = accountRepository.save(entity);

            // 显式指定泛型<AccountDTO>，解决DTO转换推断问题
            return new AccountDTO().convertFrom(savedEntity);

        } catch (Exception e) {
            log.error("保存账户失败，DTO：{}", dto, e);
            throw new RuntimeException("账户保存失败：" + e.getMessage(), e);
        }
    }

    @Override
    public Optional<AccountDTO> findById(String id) {
        log.debug("根据ID查询账户: {}", id);
        // 核心逻辑：Repository查Entity → Optional.map转换为DTO
        return accountRepository.findById(id)
                // 利用AccountDTO的convertFrom方法转换，泛型自动推断为AccountDTO
                .map(entity -> new AccountDTO().convertFrom(entity));
    }

    @Override
    public List<AccountDTO> findAll() {
        log.debug("查询所有账户");
        return accountRepository.findAll().stream()
                // 核心：显式指定泛型类型 <AccountDTO>，解决推断失效
                .map(entity -> new AccountDTO().convertFrom(entity))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        log.debug("检查账户是否存在: {}", id);
        return accountRepository.existsById(id.toString());
    }

    @Override
    public long count() {
        log.debug("统计账户数量");
        return accountRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        log.info("删除账户: {}", id);
        accountRepository.deleteById(id.toString());
    }

    @Override
    @Transactional
    public void delete(AccountDTO entity) {
        log.info("删除账户实体: {}", entity);
        if (entity.getAccountId() != null) {
            accountRepository.deleteById(entity.getAccountId());
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.info("删除所有账户");
        accountRepository.deleteAll();
    }

    @Override
    @Transactional
    public AccountDTO saveAndFlush(AccountDTO dto) {
        log.debug("保存并刷新账户实体: {}", dto);
        try {
            AccountEntity entity = new AccountEntity().convertFrom(dto);
            AccountEntity savedEntity = accountRepository.saveAndFlush(entity);
            return new AccountDTO().convertFrom(savedEntity);
        } catch (Exception e) {
            log.error("保存并刷新账户失败，DTO：{}", dto, e);
            throw new RuntimeException("保存账户失败：" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public List<AccountDTO> saveAll(List<AccountDTO> dtoList) {
        // 1. 日志记录 + 空值处理
        log.debug("批量保存账户，待保存数量：{}", CollectionUtils.isEmpty(dtoList) ? 0 : dtoList.size());
        if (CollectionUtils.isEmpty(dtoList)) {
            return List.of(); // 返回空列表，避免返回null
        }

        try {
            // 核心：显式指定泛型 <AccountEntity>，解决推断失效
            List<AccountEntity> entityList = dtoList.stream()
                    .map(dto -> new AccountEntity().<AccountEntity>convertFrom(dto))
                    .collect(Collectors.toList());

            List<AccountEntity> savedEntityList = accountRepository.saveAll(entityList);

            return savedEntityList.stream()
                    .map(entity -> new AccountDTO().<AccountDTO>convertFrom(entity))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("批量保存账户失败，待保存DTO数量：{}", dtoList.size(), e);
            throw new RuntimeException("批量保存账户失败：" + e.getMessage(), e);
        }
    }

    @Override
    public List<AccountDTO> findAllById(List<String> ids) {
        // 1. 日志记录 + 空值处理（避免NPE，返回空列表而非null）
        log.debug("批量根据ID查询账户，待查询ID数量：{}", CollectionUtils.isEmpty(ids) ? 0 : ids.size());
        if (CollectionUtils.isEmpty(ids)) {
            return List.of(); // 返回不可变空列表，符合非null集合最佳实践
        }

        try {
            // 2. 调用Repository批量查询（返回Iterable<AccountEntity>）
            Iterable<AccountEntity> entityIterable = accountRepository.findAllById(ids);

            // 3. Iterable转Stream → 转换为AccountDTO列表（复用BaseDTO的convertFrom）
            return StreamSupport.stream(entityIterable.spliterator(), false)
                    .map(entity -> new AccountDTO().convertFrom(entity))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            // 4. 异常封装：便于定位批量查询失败原因（如ID格式错误、数据库异常）
            log.error("批量根据ID查询账户失败，待查询ID数量：{}", ids.size(), e);
            throw new RuntimeException("批量查询账户失败：" + e.getMessage(), e);
        }
    }
}
