package net.ijupiter.trading.core.fund.aggregates;

import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.fund.commands.*;
import net.ijupiter.trading.api.fund.events.*;
import net.ijupiter.trading.api.fund.enums.FundAccountStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

/**
 * 资金账户聚合
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Aggregate
@NoArgsConstructor
public class FundAccountAggregate {

    @AggregateIdentifier
    private String fundAccountId;
    private String accountId;
    private BigDecimal balance;
    private BigDecimal frozenAmount;
    private FundAccountStatus status;

    /**
     * 创建资金账户命令处理器
     * 
     * @param command 创建资金账户命令
     */
    @CommandHandler
    public FundAccountAggregate(CreateFundAccountCommand command) {
        FundAccountCreatedEvent event = new FundAccountCreatedEvent(
                command.getFundAccountId(),
                command.getAccountId(),
                command.getInitialBalance(),
                BigDecimal.ZERO,
                FundAccountStatus.NORMAL
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 资金入金命令处理器
     * 
     * @param command 入金命令
     */
    @CommandHandler
    public void handle(DepositFundCommand command) {
        if (!canDeposit()) {
            throw new IllegalStateException("资金账户无法入金，当前状态：" + status);
        }

        BigDecimal newBalance = balance.add(command.getAmount());
        FundDepositedEvent event = new FundDepositedEvent(
                command.getFundAccountId(),
                command.getTransactionId(),
                command.getAmount(),
                newBalance,
                command.getDepositType(),
                command.getDescription()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 资金出金命令处理器
     * 
     * @param command 出金命令
     */
    @CommandHandler
    public void handle(WithdrawFundCommand command) {
        if (!canWithdraw()) {
            throw new IllegalStateException("资金账户无法出金，当前状态：" + status);
        }

        BigDecimal availableBalance = balance.subtract(frozenAmount);
        if (availableBalance.compareTo(command.getAmount()) < 0) {
            throw new IllegalStateException("可用余额不足，可用余额：" + availableBalance + 
                                      "，出金金额：" + command.getAmount());
        }

        BigDecimal newBalance = balance.subtract(command.getAmount());
        FundWithdrawnEvent event = new FundWithdrawnEvent(
                command.getFundAccountId(),
                command.getTransactionId(),
                command.getAmount(),
                newBalance,
                command.getWithdrawType(),
                command.getDescription()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 资金冻结命令处理器
     * 
     * @param command 冻结资金命令
     */
    @CommandHandler
    public void handle(FreezeFundCommand command) {
        if (!canFreeze()) {
            throw new IllegalStateException("资金账户无法冻结，当前状态：" + status);
        }

        BigDecimal availableBalance = balance.subtract(frozenAmount);
        if (availableBalance.compareTo(command.getAmount()) < 0) {
            throw new IllegalStateException("可用余额不足，可用余额：" + availableBalance + 
                                      "，冻结金额：" + command.getAmount());
        }

        BigDecimal newFrozenAmount = frozenAmount.add(command.getAmount());
        FundFrozenEvent event = new FundFrozenEvent(
                command.getFundAccountId(),
                command.getTransactionId(),
                command.getAmount(),
                balance,
                newFrozenAmount,
                command.getDescription()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 资金解冻命令处理器
     * 
     * @param command 解冻资金命令
     */
    @CommandHandler
    public void handle(UnfreezeFundCommand command) {
        if (!canUnfreeze()) {
            throw new IllegalStateException("资金账户无法解冻，当前状态：" + status);
        }

        if (frozenAmount.compareTo(command.getAmount()) < 0) {
            throw new IllegalStateException("冻结金额不足，冻结金额：" + frozenAmount + 
                                      "，解冻金额：" + command.getAmount());
        }

        BigDecimal newFrozenAmount = frozenAmount.subtract(command.getAmount());
        FundUnfrozenEvent event = new FundUnfrozenEvent(
                command.getFundAccountId(),
                command.getTransactionId(),
                command.getAmount(),
                balance,
                newFrozenAmount,
                command.getDescription()
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 处理资金账户创建事件
     * 
     * @param event 资金账户创建事件
     */
    @EventSourcingHandler
    public void on(FundAccountCreatedEvent event) {
        this.fundAccountId = event.getFundAccountId();
        this.accountId = event.getAccountId();
        this.balance = event.getBalance();
        this.frozenAmount = event.getFrozenAmount();
        this.status = event.getStatus();
    }

    /**
     * 处理资金入金事件
     * 
     * @param event 资金入金事件
     */
    @EventSourcingHandler
    public void on(FundDepositedEvent event) {
        this.balance = event.getBalance();
    }

    /**
     * 处理资金出金事件
     * 
     * @param event 资金出金事件
     */
    @EventSourcingHandler
    public void on(FundWithdrawnEvent event) {
        this.balance = event.getBalance();
    }

    /**
     * 处理资金冻结事件
     * 
     * @param event 资金冻结事件
     */
    @EventSourcingHandler
    public void on(FundFrozenEvent event) {
        this.frozenAmount = event.getFrozenAmount();
    }

    /**
     * 处理资金解冻事件
     * 
     * @param event 资金解冻事件
     */
    @EventSourcingHandler
    public void on(FundUnfrozenEvent event) {
        this.frozenAmount = event.getFrozenAmount();
    }

    /**
     * 判断资金账户是否可以入金
     * 
     * @return 是否可以入金
     */
    private boolean canDeposit() {
        return FundAccountStatus.NORMAL.equals(status);
    }

    /**
     * 判断资金账户是否可以出金
     * 
     * @return 是否可以出金
     */
    private boolean canWithdraw() {
        return FundAccountStatus.NORMAL.equals(status);
    }

    /**
     * 判断资金账户是否可以冻结
     * 
     * @return 是否可以冻结
     */
    private boolean canFreeze() {
        return FundAccountStatus.NORMAL.equals(status);
    }

    /**
     * 判断资金账户是否可以解冻
     * 
     * @return 是否可以解冻
     */
    private boolean canUnfreeze() {
        return FundAccountStatus.NORMAL.equals(status);
    }
}