package net.ijupiter.trading.core.customer.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ijupiter.trading.api.customer.commands.CreateCustomerCommand;
import net.ijupiter.trading.api.customer.commands.UpdateCustomerCommand;
import net.ijupiter.trading.api.customer.commands.FreezeCustomerCommand;
import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
import net.ijupiter.trading.api.customer.services.CustomerService;
import net.ijupiter.trading.core.customer.repositories.CustomerJpaRepository;
import net.ijupiter.trading.core.customer.entities.CustomerEntity;

import org.axonframework.commandhandling.gateway.CommandGateway;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 客户领域服务实现
 */
@Slf4j
@Service
@Transactional
public class CustomerDomainService implements CustomerService {
    
    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    
    @Autowired
    private CommandGateway commandGateway;
    
    @Override
    public List<CustomerDTO> findAll() {
        List<CustomerEntity> entities = customerJpaRepository.findAll();
        return entities.stream()
                .map(entity -> {
                    CustomerDTO dto = new CustomerDTO();
                    dto.convertFrom(entity);
                    // 处理字段名不一致的情况
                    dto.setPhone(entity.getMobile());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<CustomerDTO> findById(Long id) {
        Optional<CustomerEntity> entity = customerJpaRepository.findById(id);
        return entity.map(e -> {
            CustomerDTO dto = new CustomerDTO();
            dto.convertFrom(e);
            // 处理字段名不一致的情况
            dto.setPhone(e.getMobile());
            return dto;
        });
    }
    
    @Override
    public Optional<CustomerDTO> findByCustomerCode(String customerCode) {
        CustomerEntity entity = customerJpaRepository.findByCustomerCode(customerCode);
        if (entity == null) {
            return Optional.empty();
        }
        CustomerDTO dto = new CustomerDTO();
        dto.convertFrom(entity);
        // 处理字段名不一致的情况
        dto.setPhone(entity.getMobile());
        return Optional.of(dto);
    }
    
    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        CustomerEntity entity = new CustomerEntity();
        entity.convertFrom(customerDTO);
        // 处理字段名不一致的情况
        entity.setMobile(customerDTO.getPhone());
        CustomerEntity savedEntity = customerJpaRepository.save(entity);
        
        CustomerDTO resultDTO = new CustomerDTO();
        resultDTO.convertFrom(savedEntity);
        // 处理字段名不一致的情况
        resultDTO.setPhone(savedEntity.getMobile());
        return resultDTO;
    }
    
    @Override
    public void deleteById(Long id) {
        customerJpaRepository.deleteById(id);
    }
    
    @Override
    public void delete(CustomerDTO entity) {
        customerJpaRepository.deleteById(entity.getId());
    }
    
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("不支持批量删除所有客户");
    }
    
    @Override
    public CustomerDTO saveAndFlush(CustomerDTO entity) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.convertFrom(entity);
        // 处理字段名不一致的情况
        customerEntity.setMobile(entity.getPhone());
        CustomerEntity savedEntity = customerJpaRepository.save(customerEntity);
        
        CustomerDTO resultDTO = new CustomerDTO();
        resultDTO.convertFrom(savedEntity);
        // 处理字段名不一致的情况
        resultDTO.setPhone(savedEntity.getMobile());
        return resultDTO;
    }
    
    @Override
    public List<CustomerDTO> saveAll(List<CustomerDTO> entities) {
        List<CustomerEntity> customerEntities = entities.stream()
                .map(dto -> {
                    CustomerEntity entity = new CustomerEntity();
                    entity.convertFrom(dto);
                    // 处理字段名不一致的情况
                    entity.setMobile(dto.getPhone());
                    return entity;
                })
                .collect(Collectors.toList());
        
        List<CustomerEntity> savedEntities = customerJpaRepository.saveAll(customerEntities);
        
        return savedEntities.stream()
                .map(entity -> {
                    CustomerDTO dto = new CustomerDTO();
                    dto.convertFrom(entity);
                    // 处理字段名不一致的情况
                    dto.setPhone(entity.getMobile());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CustomerDTO> findAllById(List<Long> ids) {
        List<CustomerEntity> entities = customerJpaRepository.findAllById(ids);
        return entities.stream()
                .map(entity -> {
                    CustomerDTO dto = new CustomerDTO();
                    dto.convertFrom(entity);
                    // 处理字段名不一致的情况
                    dto.setPhone(entity.getMobile());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsById(Long id) {
        return customerJpaRepository.existsById(id);
    }
    
    @Override
    public long count() {
        return customerJpaRepository.count();
    }
    
    // 注意：账户相关方法已移除，因为账户管理应由Funding模块和Securities模块负责
    
    @Override
    public CustomerStatistics getCustomerStatistics() {
        // 计算客户统计信息
        long totalCustomers = customerJpaRepository.countAll();
        long activeCustomers = customerJpaRepository.countByStatus(1); // 1表示正常状态
        long frozenCustomers = customerJpaRepository.countByStatus(2); // 2表示冻结状态
        long closedCustomers = customerJpaRepository.countByStatus(3); // 3表示注销状态
        
        // TODO: 计算今日新增客户数，需要根据实际需求实现
        long todayNewCustomers = 0;
        
        return new CustomerStatistics(totalCustomers, activeCustomers, frozenCustomers, closedCustomers, todayNewCustomers);
    }
    
    @Override
    public boolean existsByCustomerCode(String customerCode) {
        return customerJpaRepository.existsByCustomerCode(customerCode);
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