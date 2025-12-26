package net.ijupiter.trading.core.customer.repositories.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
import net.ijupiter.trading.api.customer.dtos.CustomerAccountDTO;
import net.ijupiter.trading.core.customer.repositories.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 客户仓储内存实现(仅用于演示，实际项目应使用数据库实现)
 */
@Slf4j
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    
    // 内存存储，仅用于演示
    private final Map<String, CustomerDTO> customersByCode = new ConcurrentHashMap<>();
    private final Map<Long, CustomerDTO> customersById = new ConcurrentHashMap<>();
    private final Map<String, CustomerAccountDTO> accountsByCode = new ConcurrentHashMap<>();
    private final Map<Long, CustomerAccountDTO> accountsById = new ConcurrentHashMap<>();
    private final Map<Long, List<CustomerAccountDTO>> accountsByCustomerId = new ConcurrentHashMap<>();
    
    // ID生成器
    private final AtomicLong customerIdGenerator = new AtomicLong(1);
    private final AtomicLong accountIdGenerator = new AtomicLong(1);
    
    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        // 生成ID
        if (customerDTO.getId() == null) {
            customerDTO.setId(customerIdGenerator.getAndIncrement());
        }
        
        // 更新时间戳
        if (customerDTO.getCreateTime() == null) {
            customerDTO.setCreateTime(LocalDateTime.now());
        }
        customerDTO.setUpdateTime(LocalDateTime.now());
        
        // 存储
        customersById.put(customerDTO.getId(), customerDTO);
        customersByCode.put(customerDTO.getCustomerCode(), customerDTO);
        
        log.debug("已保存客户: {}", customerDTO.getCustomerCode());
        return customerDTO;
    }
    
    @Override
    public Optional<CustomerDTO> findById(Long id) {
        return Optional.ofNullable(customersById.get(id));
    }
    
    @Override
    public Optional<CustomerDTO> findByCustomerCode(String customerCode) {
        return Optional.ofNullable(customersByCode.get(customerCode));
    }
    
    @Override
    public List<CustomerDTO> findAll() {
        return new ArrayList<>(customersById.values());
    }
    
    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        if (customerDTO.getId() == null) {
            throw new IllegalArgumentException("客户ID不能为空");
        }
        
        if (!customersById.containsKey(customerDTO.getId())) {
            throw new IllegalArgumentException("客户不存在: " + customerDTO.getId());
        }
        
        customerDTO.setUpdateTime(LocalDateTime.now());
        
        customersById.put(customerDTO.getId(), customerDTO);
        customersByCode.put(customerDTO.getCustomerCode(), customerDTO);
        
        log.debug("已更新客户: {}", customerDTO.getCustomerCode());
        return customerDTO;
    }
    
    @Override
    public void deleteById(Long id) {
        CustomerDTO customer = customersById.remove(id);
        if (customer != null) {
            customersByCode.remove(customer.getCustomerCode());
            log.debug("已删除客户: {}", customer.getCustomerCode());
        }
    }
    
    @Override
    public boolean existsByCustomerCode(String customerCode) {
        return customersByCode.containsKey(customerCode);
    }
    
    @Override
    public CustomerAccountDTO saveAccount(CustomerAccountDTO accountDTO) {
        // 生成ID
        if (accountDTO.getId() == null) {
            accountDTO.setId(accountIdGenerator.getAndIncrement());
        }
        
        // 更新时间戳
        if (accountDTO.getCreateTime() == null) {
            accountDTO.setCreateTime(LocalDateTime.now());
        }
        accountDTO.setUpdateTime(LocalDateTime.now());
        
        // 计算可用余额
        if (accountDTO.getBalance() == null) {
            accountDTO.setBalance(BigDecimal.ZERO);
        }
        if (accountDTO.getFrozenAmount() == null) {
            accountDTO.setFrozenAmount(BigDecimal.ZERO);
        }
        accountDTO.setAvailableBalance(accountDTO.getBalance().subtract(accountDTO.getFrozenAmount()));
        
        // 存储
        accountsById.put(accountDTO.getId(), accountDTO);
        accountsByCode.put(accountDTO.getAccountCode(), accountDTO);
        
        // 添加到客户的账户列表
        accountsByCustomerId.computeIfAbsent(accountDTO.getCustomerId(), k -> new ArrayList<>())
                .add(accountDTO);
        
        log.debug("已保存账户: {}", accountDTO.getAccountCode());
        return accountDTO;
    }
    
    @Override
    public Optional<CustomerAccountDTO> findAccountById(Long accountId) {
        return Optional.ofNullable(accountsById.get(accountId));
    }
    
    @Override
    public Optional<CustomerAccountDTO> findAccountByCode(String accountCode) {
        return Optional.ofNullable(accountsByCode.get(accountCode));
    }
    
    @Override
    public List<CustomerAccountDTO> findAccountsByCustomerId(Long customerId) {
        return accountsByCustomerId.getOrDefault(customerId, Collections.emptyList());
    }
    
    @Override
    public CustomerAccountDTO updateAccount(CustomerAccountDTO accountDTO) {
        if (accountDTO.getId() == null) {
            throw new IllegalArgumentException("账户ID不能为空");
        }
        
        if (!accountsById.containsKey(accountDTO.getId())) {
            throw new IllegalArgumentException("账户不存在: " + accountDTO.getId());
        }
        
        accountDTO.setUpdateTime(LocalDateTime.now());
        
        // 计算可用余额
        if (accountDTO.getBalance() == null) {
            accountDTO.setBalance(BigDecimal.ZERO);
        }
        if (accountDTO.getFrozenAmount() == null) {
            accountDTO.setFrozenAmount(BigDecimal.ZERO);
        }
        accountDTO.setAvailableBalance(accountDTO.getBalance().subtract(accountDTO.getFrozenAmount()));
        
        // 更新存储
        accountsById.put(accountDTO.getId(), accountDTO);
        accountsByCode.put(accountDTO.getAccountCode(), accountDTO);
        
        // 更新客户的账户列表
        List<CustomerAccountDTO> customerAccounts = accountsByCustomerId.get(accountDTO.getCustomerId());
        if (customerAccounts != null) {
            for (int i = 0; i < customerAccounts.size(); i++) {
                if (customerAccounts.get(i).getId().equals(accountDTO.getId())) {
                    customerAccounts.set(i, accountDTO);
                    break;
                }
            }
        }
        
        log.debug("已更新账户: {}", accountDTO.getAccountCode());
        return accountDTO;
    }
    
    @Override
    public void deleteAccountById(Long accountId) {
        CustomerAccountDTO account = accountsById.remove(accountId);
        if (account != null) {
            accountsByCode.remove(account.getAccountCode());
            
            // 从客户的账户列表中移除
            List<CustomerAccountDTO> customerAccounts = accountsByCustomerId.get(account.getCustomerId());
            if (customerAccounts != null) {
                customerAccounts.removeIf(a -> a.getId().equals(accountId));
            }
            
            log.debug("已删除账户: {}", account.getAccountCode());
        }
    }
}