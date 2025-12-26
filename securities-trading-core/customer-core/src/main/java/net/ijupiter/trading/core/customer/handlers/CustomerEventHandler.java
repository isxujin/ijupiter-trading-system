package net.ijupiter.trading.core.customer.handlers;

import lombok.extern.slf4j.Slf4j;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ijupiter.trading.api.customer.events.CustomerCreatedEvent;
import net.ijupiter.trading.api.customer.events.CustomerUpdatedEvent;
import net.ijupiter.trading.api.customer.events.CustomerFrozenEvent;
import net.ijupiter.trading.core.customer.repositories.CustomerRepository;
import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
import net.ijupiter.trading.api.customer.dtos.CustomerAccountDTO;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;
import net.ijupiter.trading.api.customer.enums.CustomerType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户事件处理器
 */
@Slf4j
@Component
public class CustomerEventHandler {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    /**
     * 处理客户创建事件
     */
    @EventHandler
    public void on(CustomerCreatedEvent event) {
        log.debug("处理客户创建事件: {}", event);
        
        CustomerDTO customer = CustomerDTO.builder()
                .customerCode(event.getCustomerCode())
                .customerName(event.getCustomerName())
                .customerType(event.getCustomerType())
                .idType(event.getIdType())
                .idNumber(event.getIdNumber())
                .phone(event.getPhone())
                .email(event.getEmail())
                .address(event.getAddress())
                .status(CustomerStatus.NORMAL.getCode())
                .riskLevel(event.getRiskLevel())
                .openDate(event.getEventTime())
                .updateTime(event.getEventTime())
                .createTime(event.getEventTime())
                .remark(event.getRemark())
                .build();
        
        customerRepository.save(customer);
        
        // 自动为客户创建资金账户和证券账户
        createCustomerAccounts(customer);
        
        log.info("已创建客户: {}", event.getCustomerCode());
    }
    
    /**
     * 处理客户更新事件
     */
    @EventHandler
    public void on(CustomerUpdatedEvent event) {
        log.debug("处理客户更新事件: {}", event);
        
        CustomerDTO customer = customerRepository.findByCustomerCode(event.getCustomerCode())
                .orElseThrow(() -> new RuntimeException("客户不存在: " + event.getCustomerCode()));
        
        customer.setCustomerName(event.getCustomerName());
        customer.setPhone(event.getPhone());
        customer.setEmail(event.getEmail());
        customer.setAddress(event.getAddress());
        customer.setRiskLevel(event.getRiskLevel());
        customer.setUpdateTime(event.getEventTime());
        customer.setRemark(event.getRemark());
        
        customerRepository.update(customer);
        
        log.info("已更新客户: {}", event.getCustomerCode());
    }
    
    /**
     * 处理客户冻结事件
     */
    @EventHandler
    public void on(CustomerFrozenEvent event) {
        log.debug("处理客户冻结事件: {}", event);
        
        CustomerDTO customer = customerRepository.findByCustomerCode(event.getCustomerCode())
                .orElseThrow(() -> new RuntimeException("客户不存在: " + event.getCustomerCode()));
        
        customer.setStatus(CustomerStatus.FROZEN.getCode());
        customer.setUpdateTime(event.getEventTime());
        
        // 将冻结原因存储在备注中
        String remark = customer.getRemark() != null ? customer.getRemark() + "\n" : "";
        remark += "冻结原因: " + event.getReason();
        customer.setRemark(remark);
        
        customerRepository.update(customer);
        
        // 同时冻结客户的所有账户
        freezeCustomerAccounts(customer.getId(), event.getEventTime());
        
        log.info("已冻结客户: {}, 原因: {}", event.getCustomerCode(), event.getReason());
    }
    
    /**
     * 为客户创建默认账户
     */
    private void createCustomerAccounts(CustomerDTO customer) {
        try {
            // 创建资金账户
            CustomerAccountDTO fundingAccount = CustomerAccountDTO.builder()
                    .customerId(customer.getId())
                    .customerCode(customer.getCustomerCode())
                    .accountCode(generateAccountCode(1))
                    .accountType(1) // 资金账户
                    .accountName(customer.getCustomerName() + "的资金账户")
                    .balance(java.math.BigDecimal.ZERO)
                    .frozenAmount(java.math.BigDecimal.ZERO)
                    .availableBalance(java.math.BigDecimal.ZERO)
                    .status(CustomerStatus.NORMAL.getCode())
                    .openDate(customer.getOpenDate())
                    .updateTime(customer.getUpdateTime())
                    .createTime(customer.getCreateTime())
                    .remark("系统自动创建")
                    .build();
            
            customerRepository.saveAccount(fundingAccount);
            
            // 创建证券账户
            CustomerAccountDTO securitiesAccount = CustomerAccountDTO.builder()
                    .customerId(customer.getId())
                    .customerCode(customer.getCustomerCode())
                    .accountCode(generateAccountCode(2))
                    .accountType(2) // 证券账户
                    .accountName(customer.getCustomerName() + "的证券账户")
                    .balance(java.math.BigDecimal.ZERO)
                    .frozenAmount(java.math.BigDecimal.ZERO)
                    .availableBalance(java.math.BigDecimal.ZERO)
                    .status(CustomerStatus.NORMAL.getCode())
                    .openDate(customer.getOpenDate())
                    .updateTime(customer.getUpdateTime())
                    .createTime(customer.getCreateTime())
                    .remark("系统自动创建")
                    .build();
            
            customerRepository.saveAccount(securitiesAccount);
            
            log.info("已为客户 {} 创建默认账户", customer.getCustomerCode());
        } catch (Exception e) {
            log.error("为客户 {} 创建默认账户失败", customer.getCustomerCode(), e);
            // 不抛出异常，避免影响客户创建流程
        }
    }
    
    /**
     * 冻结客户的所有账户
     */
    private void freezeCustomerAccounts(Long customerId, LocalDateTime eventTime) {
        try {
            List<CustomerAccountDTO> accounts = customerRepository.findAccountsByCustomerId(customerId);
            for (CustomerAccountDTO account : accounts) {
                if (account.getStatus() == CustomerStatus.NORMAL.getCode()) {
                    account.setStatus(CustomerStatus.FROZEN.getCode());
                    account.setUpdateTime(eventTime);
                    customerRepository.updateAccount(account);
                }
            }
            log.info("已冻结客户 {} 的所有账户", customerId);
        } catch (Exception e) {
            log.error("冻结客户 {} 的账户失败", customerId, e);
        }
    }
    
    /**
     * 生成账户编号
     */
    private String generateAccountCode(Integer accountType) {
        String prefix = accountType == 1 ? "F" : "S"; // F:资金账户, S:证券账户
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 8);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return prefix + suffix + random;
    }
}