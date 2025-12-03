package net.ijupiter.trading.core.query.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.fund.events.*;
import net.ijupiter.trading.core.query.model.FundAccountView;
import net.ijupiter.trading.core.query.repository.AccountViewRepository;
import net.ijupiter.trading.core.query.repository.FundAccountViewRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金账户事件处理器
 * 
 * @author ijupiter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FundAccountEventHandler {
    
    private final FundAccountViewRepository fundAccountViewRepository;
    private final AccountViewRepository accountViewRepository;
    
    @EventHandler
    public void on(FundAccountCreatedEvent event) {
        log.debug("处理资金账户创建事件: {}", event.getFundAccountId());
        
        accountViewRepository.findById(event.getAccountId())
                .ifPresent(account -> {
                    FundAccountView fundAccountView = FundAccountView.builder()
                            .fundAccountId(event.getFundAccountId())
                            .customerId(account.getCustomerId())
                            .accountId(event.getAccountId())
                            .accountNo(account.getAccountNo())
                            .accountName(account.getAccountName())
                            .accountType("SECURITIES")
                            .status(event.getStatus().name())
                            .totalAssets(event.getBalance())
                            .availableBalance(event.getBalance().subtract(event.getFrozenAmount()))
                            .frozenBalance(event.getFrozenAmount())
                            .transitBalance(BigDecimal.ZERO)
                            .currency("CNY")
                            .createTime(event.getCreateTime())
                            .updateTime(event.getCreateTime())
                            .build();
                            
                    fundAccountViewRepository.save(fundAccountView);
                });
    }
    
    @EventHandler
    public void on(FundDepositedEvent event) {
        log.debug("处理资金存入事件: {}", event.getFundAccountId());
        
        fundAccountViewRepository.findById(event.getFundAccountId())
                .ifPresent(fundAccountView -> {
                    BigDecimal newTotalAssets = fundAccountView.getTotalAssets().add(event.getAmount());
                    BigDecimal newAvailableBalance = fundAccountView.getAvailableBalance().add(event.getAmount());
                    
                    fundAccountView.setTotalAssets(newTotalAssets);
                    fundAccountView.setAvailableBalance(newAvailableBalance);
                    fundAccountView.setUpdateTime(LocalDateTime.now());
                    fundAccountViewRepository.save(fundAccountView);
                });
    }
    
    @EventHandler
    public void on(FundWithdrawnEvent event) {
        log.debug("处理资金取出事件: {}", event.getFundAccountId());
        
        fundAccountViewRepository.findById(event.getFundAccountId())
                .ifPresent(fundAccountView -> {
                    BigDecimal newTotalAssets = fundAccountView.getTotalAssets().subtract(event.getAmount());
                    BigDecimal newAvailableBalance = fundAccountView.getAvailableBalance().subtract(event.getAmount());
                    
                    fundAccountView.setTotalAssets(newTotalAssets);
                    fundAccountView.setAvailableBalance(newAvailableBalance);
                    fundAccountView.setUpdateTime(LocalDateTime.now());
                    fundAccountViewRepository.save(fundAccountView);
                });
    }
    
    @EventHandler
    public void on(FundFrozenEvent event) {
        log.debug("处理资金冻结事件: {}", event.getFundAccountId());
        
        fundAccountViewRepository.findById(event.getFundAccountId())
                .ifPresent(fundAccountView -> {
                    BigDecimal newFrozenBalance = fundAccountView.getFrozenBalance().add(event.getAmount());
                    BigDecimal newAvailableBalance = fundAccountView.getAvailableBalance().subtract(event.getAmount());
                    
                    fundAccountView.setFrozenBalance(newFrozenBalance);
                    fundAccountView.setAvailableBalance(newAvailableBalance);
                    fundAccountView.setUpdateTime(LocalDateTime.now());
                    fundAccountViewRepository.save(fundAccountView);
                });
    }
    
    @EventHandler
    public void on(FundUnfrozenEvent event) {
        log.debug("处理资金解冻事件: {}", event.getFundAccountId());
        
        fundAccountViewRepository.findById(event.getFundAccountId())
                .ifPresent(fundAccountView -> {
                    BigDecimal newFrozenBalance = fundAccountView.getFrozenBalance().subtract(event.getAmount());
                    BigDecimal newAvailableBalance = fundAccountView.getAvailableBalance().add(event.getAmount());
                    
                    fundAccountView.setFrozenBalance(newFrozenBalance);
                    fundAccountView.setAvailableBalance(newAvailableBalance);
                    fundAccountView.setUpdateTime(LocalDateTime.now());
                    fundAccountViewRepository.save(fundAccountView);
                });
    }
    
//    @EventHandler
//    public void on(FundTransitedEvent event) {
//        log.debug("处理资金在途事件: {}", event.getFundAccountId());
//
//        fundAccountViewRepository.findById(event.getFundAccountId())
//                .ifPresent(fundAccountView -> {
//                    BigDecimal newAvailableBalance = fundAccountView.getAvailableBalance().subtract(event.getAmount());
//                    BigDecimal newTransitBalance = fundAccountView.getTransitBalance().add(event.getAmount());
//
//                    fundAccountView.setAvailableBalance(newAvailableBalance);
//                    fundAccountView.setTransitBalance(newTransitBalance);
//                    fundAccountView.setUpdateTime(LocalDateTime.now());
//                    fundAccountViewRepository.save(fundAccountView);
//                });
//    }
}