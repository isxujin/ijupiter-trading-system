package net.ijupiter.trading.core.account.aggregates;

import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.account.commands.CloseAccountCommand;
import net.ijupiter.trading.api.account.commands.CreateAccountCommand;
import net.ijupiter.trading.api.account.commands.UpdateAccountCommand;
import net.ijupiter.trading.api.account.enums.AccountStatus;
import net.ijupiter.trading.api.account.enums.AccountType;
import net.ijupiter.trading.api.account.events.AccountClosedEvent;
import net.ijupiter.trading.api.account.events.AccountCreatedEvent;
import net.ijupiter.trading.api.account.events.AccountUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * 账户聚合
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Aggregate
@NoArgsConstructor
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private String userId;
    private String accountName;
    private AccountType accountType;
    private AccountStatus accountStatus;

    /**
     * 创建账户命令处理器
     * 
     * @param command 创建账户命令
     */
    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        AccountCreatedEvent event = new AccountCreatedEvent(
                command.getAccountId(),
                command.getUserId(),
                command.getAccountName(),
                command.getAccountType(),
                AccountStatus.NORMAL
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 更新账户命令处理器
     * 
     * @param command 更新账户命令
     */
    @CommandHandler
    public void handle(UpdateAccountCommand command) {
        if (!canUpdate()) {
            throw new IllegalStateException("账户无法更新，当前状态：" + accountStatus);
        }

        AccountUpdatedEvent event = new AccountUpdatedEvent(
                command.getAccountId(),
                command.getAccountName()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 关闭账户命令处理器
     * 
     * @param command 关闭账户命令
     */
    @CommandHandler
    public void handle(CloseAccountCommand command) {
        if (!canClose()) {
            throw new IllegalStateException("账户无法关闭，当前状态：" + accountStatus);
        }

        AccountClosedEvent event = new AccountClosedEvent(
                command.getAccountId(),
                command.getReason() != null ? command.getReason() : "用户关闭账户"
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 处理账户创建事件
     * 
     * @param event 账户创建事件
     */
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.userId = event.getUserId();
        this.accountName = event.getAccountName();
        this.accountType = event.getAccountType();
        this.accountStatus = event.getStatus();
    }

    /**
     * 处理账户更新事件
     * 
     * @param event 账户更新事件
     */
    @EventSourcingHandler
    public void on(AccountUpdatedEvent event) {
        this.accountName = event.getAccountName();
    }

    /**
     * 处理账户关闭事件
     * 
     * @param event 账户关闭事件
     */
    @EventSourcingHandler
    public void on(AccountClosedEvent event) {
        this.accountStatus = AccountStatus.CLOSED;
    }

    /**
     * 判断账户是否可以更新
     * 
     * @return 是否可以更新
     */
    private boolean canUpdate() {
        return AccountStatus.NORMAL.equals(accountStatus);
    }

    /**
     * 判断账户是否可以关闭
     * 
     * @return 是否可以关闭
     */
    private boolean canClose() {
        return AccountStatus.NORMAL.equals(accountStatus) || 
               AccountStatus.FROZEN.equals(accountStatus) || 
               AccountStatus.LOCKED.equals(accountStatus);
    }
}