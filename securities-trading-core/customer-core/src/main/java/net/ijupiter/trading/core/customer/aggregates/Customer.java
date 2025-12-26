package net.ijupiter.trading.core.customer.aggregates;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import net.ijupiter.trading.api.customer.commands.CreateCustomerCommand;
import net.ijupiter.trading.api.customer.commands.UpdateCustomerCommand;
import net.ijupiter.trading.api.customer.commands.FreezeCustomerCommand;
import net.ijupiter.trading.api.customer.events.CustomerCreatedEvent;
import net.ijupiter.trading.api.customer.events.CustomerUpdatedEvent;
import net.ijupiter.trading.api.customer.events.CustomerFrozenEvent;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;
import net.ijupiter.trading.api.customer.enums.CustomerType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * 客户聚合根
 */
@Aggregate
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Customer {
    
    /**
     * 客户编号(聚合标识符)
     */
    @AggregateIdentifier
    private String customerCode;
    
    /**
     * 客户名称
     */
    private String customerName;
    
    /**
     * 客户类型
     */
    private CustomerType customerType;
    
    /**
     * 证件类型
     */
    private Integer idType;
    
    /**
     * 证件号码
     */
    private String idNumber;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 电子邮箱
     */
    private String email;
    
    /**
     * 联系地址
     */
    private String address;
    
    /**
     * 客户状态
     */
    private CustomerStatus status;
    
    /**
     * 风险等级
     */
    private Integer riskLevel;
    
    /**
     * 开户日期
     */
    private LocalDateTime openDate;
    
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建客户命令处理器
     */
    @CommandHandler
    public Customer(CreateCustomerCommand command) {
        log.info("处理创建客户命令: {}", command);
        
        // 生成客户编号
        String customerCode = generateCustomerCode();
        
        // 应用客户创建事件
        CustomerCreatedEvent event = CustomerCreatedEvent.builder()
                .customerCode(customerCode)
                .customerName(command.getCustomerName())
                .customerType(command.getCustomerType())
                .idType(command.getIdType())
                .idNumber(command.getIdNumber())
                .phone(command.getPhone())
                .email(command.getEmail())
                .address(command.getAddress())
                .riskLevel(command.getRiskLevel())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .remark(command.getRemark())
                .build();
                
        apply(event);
    }
    
    /**
     * 更新客户命令处理器
     */
    @CommandHandler
    public void handle(UpdateCustomerCommand command) {
        log.info("处理更新客户命令: {}", command);
        
        // 检查客户状态
        if (CustomerStatus.CLOSED.equals(this.status)) {
            throw new IllegalStateException("已注销的客户无法更新");
        }
        
        // 应用客户更新事件
        CustomerUpdatedEvent event = CustomerUpdatedEvent.builder()
                .customerCode(this.customerCode)
                .customerName(command.getCustomerName())
                .phone(command.getPhone())
                .email(command.getEmail())
                .address(command.getAddress())
                .riskLevel(command.getRiskLevel())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .remark(command.getRemark())
                .build();
                
        apply(event);
    }
    
    /**
     * 冻结客户命令处理器
     */
    @CommandHandler
    public void handle(FreezeCustomerCommand command) {
        log.info("处理冻结客户命令: {}", command);
        
        // 检查客户状态
        if (CustomerStatus.FROZEN.equals(this.status)) {
            throw new IllegalStateException("客户已经是冻结状态");
        }
        
        if (CustomerStatus.CLOSED.equals(this.status)) {
            throw new IllegalStateException("已注销的客户无法冻结");
        }
        
        // 应用客户冻结事件
        CustomerFrozenEvent event = CustomerFrozenEvent.builder()
                .customerCode(this.customerCode)
                .reason(command.getReason())
                .operatorId(command.getOperatorId())
                .eventTime(LocalDateTime.now())
                .build();
                
        apply(event);
    }
    
    /**
     * 客户创建事件处理器
     */
    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        log.debug("应用客户创建事件: {}", event);
        
        this.customerCode = event.getCustomerCode();
        this.customerName = event.getCustomerName();
        this.customerType = CustomerType.fromCode(event.getCustomerType());
        this.idType = event.getIdType();
        this.idNumber = event.getIdNumber();
        this.phone = event.getPhone();
        this.email = event.getEmail();
        this.address = event.getAddress();
        this.status = CustomerStatus.NORMAL;
        this.riskLevel = event.getRiskLevel();
        this.openDate = event.getEventTime();
        this.updateTime = event.getEventTime();
        this.remark = event.getRemark();
    }
    
    /**
     * 客户更新事件处理器
     */
    @EventSourcingHandler
    public void on(CustomerUpdatedEvent event) {
        log.debug("应用客户更新事件: {}", event);
        
        this.customerName = event.getCustomerName();
        this.phone = event.getPhone();
        this.email = event.getEmail();
        this.address = event.getAddress();
        this.riskLevel = event.getRiskLevel();
        this.updateTime = event.getEventTime();
        this.remark = event.getRemark();
    }
    
    /**
     * 客户冻结事件处理器
     */
    @EventSourcingHandler
    public void on(CustomerFrozenEvent event) {
        log.debug("应用客户冻结事件: {}", event);
        
        this.status = CustomerStatus.FROZEN;
        this.updateTime = event.getEventTime();
        // 将冻结原因存储在备注中
        this.remark = (this.remark != null ? this.remark + "\n" : "") + 
                     "冻结原因: " + event.getReason();
    }
    
    /**
     * 生成客户编号
     */
    private String generateCustomerCode() {
        // 简单生成规则: C + 时间戳后6位 + 随机4位数字
        String timestamp = String.valueOf(System.currentTimeMillis());
        String suffix = timestamp.substring(timestamp.length() - 6);
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "C" + suffix + random;
    }
}