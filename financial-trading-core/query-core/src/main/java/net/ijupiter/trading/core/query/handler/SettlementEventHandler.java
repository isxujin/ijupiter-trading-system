package net.ijupiter.trading.core.query.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.settlement.events.SettlementCompletedEvent;
import net.ijupiter.trading.api.settlement.events.SettlementFailedEvent;
import net.ijupiter.trading.core.query.repository.OrderViewRepository;
import net.ijupiter.trading.core.query.repository.ProductViewRepository;
import net.ijupiter.trading.core.query.repository.SettlementViewRepository;
import net.ijupiter.trading.core.query.repository.TradeViewRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 结算事件处理器
 * 
 * @author ijupiter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SettlementEventHandler {
    
    private final SettlementViewRepository settlementViewRepository;
    private final TradeViewRepository tradeViewRepository;
    private final OrderViewRepository orderViewRepository;
    private final ProductViewRepository productViewRepository;
    
//    @EventHandler
//    public void on(SettlementCreatedEvent event) {
//        log.debug("处理结算创建事件: {}", event.getSettlementId());
//
//        // 获取成交信息
//        tradeViewRepository.findById(event.getTradeId())
//                .ifPresent(tradeView -> {
//                    // 获取订单信息
//                    orderViewRepository.findById(tradeView.getOrderId())
//                            .ifPresent(orderView -> {
//                                // 获取产品信息
//                                productViewRepository.findById(tradeView.getProductCode())
//                                        .ifPresent(productView -> {
//                                            // 简单的费用计算逻辑
//                                            BigDecimal commission = calculateCommission(tradeView.getAmount());
//                                            BigDecimal tax = calculateTax(tradeView.getAmount());
//
//                                            SettlementView settlementView = SettlementView.builder()
//                                                    .settlementId(event.getSettlementId())
//                                                    .orderId(tradeView.getOrderId())
//                                                    .tradeId(tradeView.getTradeId())
//                                                    .customerId(tradeView.getCustomerId())
//                                                    .accountId(tradeView.getAccountId())
//                                                    .productCode(tradeView.getProductCode())
//                                                    .settlementDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
//                                                    .settlementAmount(tradeView.getAmount())
//                                                    .settlementQuantity(tradeView.getQuantity())
//                                                    .settlementPrice(tradeView.getPrice())
//                                                    .commission(commission)
//                                                    .tax(tax)
//                                                    .otherFee(BigDecimal.ZERO)
//                                                    .status("PENDING")
//                                                    .createTime(event.getCreateTime())
//                                                    .updateTime(event.getCreateTime())
//                                                    .build();
//
//                                            settlementViewRepository.save(settlementView);
//                                        });
//                            });
//                });
//    }
    
    @EventHandler
    public void on(SettlementCompletedEvent event) {
        log.debug("处理结算完成事件: {}", event.getSettlementId());
        
        settlementViewRepository.findById(event.getSettlementId())
                .ifPresent(settlementView -> {
                    settlementView.setStatus("COMPLETED");
                    settlementView.setSettlementTime(event.getCompletedTime());
                    settlementView.setUpdateTime(event.getCompletedTime());
                    settlementViewRepository.save(settlementView);
                });
    }
    
    @EventHandler
    public void on(SettlementFailedEvent event) {
        log.debug("处理结算失败事件: {}", event.getSettlementId());
        
        settlementViewRepository.findById(event.getSettlementId())
                .ifPresent(settlementView -> {
                    settlementView.setStatus("FAILED");
                    settlementView.setUpdateTime(event.getFailedTime());
                    settlementViewRepository.save(settlementView);
                });
    }
    
    /**
     * 计算手续费
     */
    private BigDecimal calculateCommission(BigDecimal amount) {
        // 手续费率：0.03%
        return amount.multiply(BigDecimal.valueOf(0.0003));
    }
    
    /**
     * 计算印花税
     */
    private BigDecimal calculateTax(BigDecimal amount) {
        // 印花税率：0.1%
        return amount.multiply(BigDecimal.valueOf(0.001));
    }
}