package net.ijupiter.trading.core.customer.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ijupiter.trading.api.customer.commands.CreateCustomerCommand;
import net.ijupiter.trading.api.customer.commands.UpdateCustomerCommand;
import net.ijupiter.trading.api.customer.commands.FreezeCustomerCommand;
import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
import net.ijupiter.trading.api.customer.dtos.CustomerAccountDTO;
import net.ijupiter.trading.api.customer.services.CustomerService;
import net.ijupiter.trading.core.customer.repositories.CustomerRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 客户领域服务实现
 */
@Slf4j
@Service
@Transactional
public class CustomerDomainService implements CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll();
    }
    
    @Override
    public Optional<CustomerDTO> findById(Long id) {
        return customerRepository.findById(id);
    }
    
    @Override
    public Optional<CustomerDTO> findByCustomerCode(String customerCode) {
        return customerRepository.findByCustomerCode(customerCode);
    }
    
    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        return customerRepository.save(customerDTO);
    }

    public CustomerDTO update(CustomerDTO customerDTO) {
        return customerRepository.update(customerDTO);
    }
    
    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
    
    @Override
    public void delete(CustomerDTO customerDTO) {
        customerRepository.deleteById(customerDTO.getId());
    }
    
    @Override
    public void deleteAll() {
        // 实际项目中不应该提供此方法，这里仅为了符合接口规范
        throw new UnsupportedOperationException("不支持批量删除所有客户");
    }
    
    @Override
    public CustomerDTO saveAndFlush(CustomerDTO customerDTO) {
        CustomerDTO saved = customerRepository.save(customerDTO);
        // 在真实实现中应该刷新到数据库
        return saved;
    }
    
    @Override
    public List<CustomerDTO> saveAll(List<CustomerDTO> customerDTOs) {
        return customerDTOs.stream()
                .map(customerRepository::save)
                .toList();
    }
    
    @Override
    public List<CustomerDTO> findAllById(List<Long> ids) {
        return ids.stream()
                .map(customerRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
    
    @Override
    public boolean existsById(Long id) {
        return customerRepository.findById(id).isPresent();
    }
    
    @Override
    public long count() {
        return customerRepository.findAll().size();
    }
    
    @Override
    public boolean existsByCustomerCode(String customerCode) {
        return customerRepository.existsByCustomerCode(customerCode);
    }
    
    @Override
    public List<CustomerAccountDTO> findCustomerAccounts(Long customerId) {
        return customerRepository.findAccountsByCustomerId(customerId);
    }
    
    @Override
    public Optional<CustomerAccountDTO> findCustomerAccountByCode(String accountCode) {
        return customerRepository.findAccountByCode(accountCode);
    }
    
    @Override
    public CustomerAccountDTO createAccount(CustomerAccountDTO accountDTO) {
        return customerRepository.saveAccount(accountDTO);
    }
    
    @Override
    public CustomerAccountDTO updateAccount(CustomerAccountDTO accountDTO) {
        return customerRepository.updateAccount(accountDTO);
    }
    
    @Override
    public boolean updateAccountStatus(Long accountId, Integer status) {
        Optional<CustomerAccountDTO> accountOpt = customerRepository.findAccountById(accountId);
        if (accountOpt.isPresent()) {
            CustomerAccountDTO account = accountOpt.get();
            account.setStatus(status);
            customerRepository.updateAccount(account);
            return true;
        }
        return false;
    }
    
    @Override
    public CustomerStatistics getCustomerStatistics() {
        List<CustomerDTO> allCustomers = findAll();
        
        long totalCustomers = allCustomers.size();
        long activeCustomers = allCustomers.stream()
                .filter(c -> c.getStatus() == 1)
                .count();
        long frozenCustomers = allCustomers.stream()
                .filter(c -> c.getStatus() == 2)
                .count();
        long closedCustomers = allCustomers.stream()
                .filter(c -> c.getStatus() == 3)
                .count();
        
        // 今日新增客户(简化处理，实际应该从数据库查询)
        long todayNewCustomers = 0;
        
        return new CustomerStatistics(totalCustomers, activeCustomers, 
                                    frozenCustomers, closedCustomers, todayNewCustomers);
    }
    
    /**
     * 创建客户(通过命令)
     */
    public CompletableFuture<String> createCustomer(CreateCustomerCommand command) {
        log.info("发送创建客户命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 更新客户(通过命令)
     */
    public CompletableFuture<Void> updateCustomer(UpdateCustomerCommand command) {
        log.info("发送更新客户命令: {}", command);
        return commandGateway.send(command);
    }
    
    /**
     * 冻结客户(通过命令)
     */
    public CompletableFuture<Void> freezeCustomer(FreezeCustomerCommand command) {
        log.info("发送冻结客户命令: {}", command);
        return commandGateway.send(command);
    }
}