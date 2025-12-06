package net.ijupiter.trading.core.customer.aggregates;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.customer.commands.*;
import net.ijupiter.trading.api.customer.enums.CustomerStatus;
import net.ijupiter.trading.api.customer.events.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDateTime;

/**
 * 客户聚合
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Aggregate
@NoArgsConstructor
@Slf4j
public class CustomerAggregate {

    @AggregateIdentifier
    private String customerId;
    private String customerCode;
    private String customerName;
    private String idCardNumber;
    private String phoneNumber;
    private String email;
    private String address;
    private String riskLevel;
    private CustomerStatus status;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
    private Long version;

    /**
     * 创建客户命令处理器
     * 
     * @param command 创建客户命令
     */
    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand command) {
        log.info("处理创建客户命令: {}", command.getCustomerId());
        
        CustomerCreatedEvent event = new CustomerCreatedEvent(
                command.getCustomerId(),
                command.getCustomerCode(),
                command.getCustomerName(),
                command.getIdCardNumber(),
                command.getPhoneNumber(),
                command.getEmail(),
                command.getAddress(),
                command.getRiskLevel(),
                CustomerStatus.NORMAL
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 更新客户信息命令处理器
     * 
     * @param command 更新客户信息命令
     */
    @CommandHandler
    public void handle(UpdateCustomerCommand command) {
        log.info("处理更新客户信息命令: {}", command.getCustomerId());
        
        CustomerUpdatedEvent event = new CustomerUpdatedEvent(
                command.getCustomerId(),
                command.getCustomerName(),
                command.getPhoneNumber(),
                command.getEmail(),
                command.getAddress(),
                command.getRiskLevel()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 冻结客户命令处理器
     * 
     * @param command 冻结客户命令
     */
    @CommandHandler
    public void handle(FreezeCustomerCommand command) {
        log.info("处理冻结客户命令: {}", command.getCustomerId());
        
        if (CustomerStatus.CANCELLED.equals(this.status)) {
            throw new IllegalStateException("已注销的客户不能冻结");
        }
        
        if (CustomerStatus.FROZEN.equals(this.status)) {
            throw new IllegalStateException("客户已经处于冻结状态");
        }
        
        CustomerFrozenEvent event = new CustomerFrozenEvent(
                command.getCustomerId(),
                CustomerStatus.FROZEN,
                command.getReason()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 解冻客户命令处理器
     * 
     * @param command 解冻客户命令
     */
    @CommandHandler
    public void handle(UnfreezeCustomerCommand command) {
        log.info("处理解冻客户命令: {}", command.getCustomerId());
        
        if (!CustomerStatus.FROZEN.equals(this.status)) {
            throw new IllegalStateException("只有冻结状态的客户才能解冻");
        }
        
        CustomerUnfrozenEvent event = new CustomerUnfrozenEvent(
                command.getCustomerId(),
                CustomerStatus.NORMAL,
                command.getReason()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 注销客户命令处理器
     * 
     * @param command 注销客户命令
     */
    @CommandHandler
    public void handle(CancelCustomerCommand command) {
        log.info("处理注销客户命令: {}", command.getCustomerId());
        
        if (CustomerStatus.CANCELLED.equals(this.status)) {
            throw new IllegalStateException("客户已经处于注销状态");
        }
        
        CustomerCancelledEvent event = new CustomerCancelledEvent(
                command.getCustomerId(),
                CustomerStatus.CANCELLED,
                command.getReason()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 客户创建事件处理器
     */
    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        log.info("处理客户创建事件: {}", event.getCustomerId());
        
        this.customerId = event.getCustomerId();
        this.customerCode = event.getCustomerCode();
        this.customerName = event.getCustomerName();
        this.idCardNumber = event.getIdCardNumber();
        this.phoneNumber = event.getPhoneNumber();
        this.email = event.getEmail();
        this.address = event.getAddress();
        this.riskLevel = event.getRiskLevel();
        this.status = event.getStatus();
        this.createTime = event.getCreateTime();
        this.version = 0L;
    }

    /**
     * 客户更新事件处理器
     */
    @EventSourcingHandler
    public void on(CustomerUpdatedEvent event) {
        log.info("处理客户更新事件: {}", event.getCustomerId());
        
        if (event.getCustomerName() != null) {
            this.customerName = event.getCustomerName();
        }
        if (event.getPhoneNumber() != null) {
            this.phoneNumber = event.getPhoneNumber();
        }
        if (event.getEmail() != null) {
            this.email = event.getEmail();
        }
        if (event.getAddress() != null) {
            this.address = event.getAddress();
        }
        if (event.getRiskLevel() != null) {
            this.riskLevel = event.getRiskLevel();
        }
        this.version++;
    }

    /**
     * 客户冻结事件处理器
     */
    @EventSourcingHandler
    public void on(CustomerFrozenEvent event) {
        log.info("处理客户冻结事件: {}", event.getCustomerId());
        
        this.status = event.getStatus();
        this.version++;
    }

    /**
     * 客户解冻事件处理器
     */
    @EventSourcingHandler
    public void on(CustomerUnfrozenEvent event) {
        log.info("处理客户解冻事件: {}", event.getCustomerId());
        
        this.status = event.getStatus();
        this.version++;
    }

    /**
     * 客户注销事件处理器
     */
    @EventSourcingHandler
    public void on(CustomerCancelledEvent event) {
        log.info("处理客户注销事件: {}", event.getCustomerId());
        
        this.status = event.getStatus();
        this.version++;
    }
}