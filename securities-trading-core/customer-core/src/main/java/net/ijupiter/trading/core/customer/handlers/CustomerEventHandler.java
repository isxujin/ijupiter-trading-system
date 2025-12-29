package net.ijupiter.trading.core.customer.handlers;

import lombok.extern.slf4j.Slf4j;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.ijupiter.trading.api.customer.events.CustomerCreatedEvent;
import net.ijupiter.trading.api.customer.events.CustomerUpdatedEvent;
import net.ijupiter.trading.api.customer.events.CustomerFrozenEvent;
import net.ijupiter.trading.core.customer.repositories.CustomerJpaRepository;
import net.ijupiter.trading.core.customer.entities.CustomerEntity;
import net.ijupiter.trading.api.customer.dtos.CustomerDTO;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;

/**
 * 客户事件处理器
 */
@Slf4j
@Component
public class CustomerEventHandler {
    
    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    
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
        
        CustomerEntity entity = new CustomerEntity();
        entity.convertFrom(customer);
        // 处理字段名不一致的情况
        entity.setMobile(customer.getPhone());
        customerJpaRepository.save(entity);
        
        // 注意：账户创建应该委托给Funding模块和Securities模块
        // 这里只记录日志，实际实现应该调用外部服务
        log.info("已创建客户: {}, 账户创建应委托给Funding和Securities模块", event.getCustomerCode());
    }
    
    /**
     * 处理客户更新事件
     */
    @EventHandler
    public void on(CustomerUpdatedEvent event) {
        log.debug("处理客户更新事件: {}", event);
        
        CustomerEntity entity = customerJpaRepository.findByCustomerCode(event.getCustomerCode());
        if (entity == null) {
            throw new RuntimeException("客户不存在: " + event.getCustomerCode());
        }
        
        entity.setCustomerName(event.getCustomerName());
        entity.setMobile(event.getPhone());
        entity.setEmail(event.getEmail());
        entity.setAddress(event.getAddress());
        entity.setRiskLevel(event.getRiskLevel());
        entity.setUpdateTime(event.getEventTime());
        entity.setRemark(event.getRemark());
        
        customerJpaRepository.save(entity);
        
        log.info("已更新客户: {}", event.getCustomerCode());
    }
    
    /**
     * 处理客户冻结事件
     */
    @EventHandler
    public void on(CustomerFrozenEvent event) {
        log.debug("处理客户冻结事件: {}", event);
        
        CustomerEntity entity = customerJpaRepository.findByCustomerCode(event.getCustomerCode());
        if (entity == null) {
            throw new RuntimeException("客户不存在: " + event.getCustomerCode());
        }
        
        entity.setStatus(CustomerStatus.FROZEN.getCode());
        entity.setUpdateTime(event.getEventTime());
        
        // 将冻结原因存储在备注中
        String remark = entity.getRemark() != null ? entity.getRemark() + "\n" : "";
        remark += "冻结原因: " + event.getReason();
        entity.setRemark(remark);
        
        customerJpaRepository.save(entity);
        
        // 注意：账户冻结应该委托给Funding模块和Securities模块
        // 这里只记录日志，实际实现应该调用外部服务
        log.info("已冻结客户: {}, 原因: {}, 账户冻结应委托给Funding和Securities模块", 
                event.getCustomerCode(), event.getReason());
    }
    
    // 注意：账户相关的方法已移除，因为账户管理应该由Funding和Securities模块负责
}