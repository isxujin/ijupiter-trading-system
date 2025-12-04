package net.ijupiter.trading.core.query.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.account.events.AccountCreatedEvent;
import net.ijupiter.trading.core.query.model.AccountView;
import net.ijupiter.trading.core.query.repositories.AccountViewRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 账户事件处理器
 * 
 * @author ijupiter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountEventHandler {
    
    private final AccountViewRepository accountViewRepository;
    
    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.debug("处理账户创建事件: {}", event.getAccountId());
        
        AccountView accountView = AccountView.builder()
                .accountId(event.getAccountId())
                .customerId(event.getUserId())
                .accountNo(generateAccountNo(event.getAccountId()))
                .accountName(event.getAccountName())
                .accountType(event.getAccountType().name())
                .status(event.getStatus().name())
                .balance(BigDecimal.ZERO)
                .availableBalance(BigDecimal.ZERO)
                .frozenAmount(BigDecimal.ZERO)
                .currency("CNY")
                .createTime(event.getCreateTime())
                .updateTime(event.getCreateTime())
                .build();
                
        accountViewRepository.save(accountView);
    }
    
//    @EventHandler
//    public void on(AccountStatusChangedEvent event) {
//        log.debug("处理账户状态变更事件: {}", event.getAccountId());
//
//        accountViewRepository.findById(event.getAccountId())
//                .ifPresent(accountView -> {
//                    accountView.setStatus(event.getNewStatus().name());
//                    accountView.setUpdateTime(LocalDateTime.now());
//                    accountViewRepository.save(accountView);
//                });
//    }
//
//    @EventHandler
//    public void on(AccountBalanceChangedEvent event) {
//        log.debug("处理账户余额变更事件: {}", event.getAccountId());
//
//        accountViewRepository.findById(event.getAccountId())
//                .ifPresent(accountView -> {
//                    accountView.setBalance(event.getNewBalance());
//                    accountView.setAvailableBalance(event.getAvailableBalance());
//                    accountView.setFrozenAmount(event.getFrozenAmount());
//                    accountView.setUpdateTime(LocalDateTime.now());
//                    accountViewRepository.save(accountView);
//                });
//    }
    
    private String generateAccountNo(String accountId) {
        // 简单的账户编号生成逻辑
        return "ACC" + System.currentTimeMillis() + accountId.substring(Math.max(0, accountId.length() - 6));
    }
}