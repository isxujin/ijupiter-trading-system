package net.ijupiter.trading.core.query.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.engine.events.TradeExecutedEvent;
import net.ijupiter.trading.core.query.model.TradeView;
import net.ijupiter.trading.core.query.repositories.AccountViewRepository;
import net.ijupiter.trading.core.query.repositories.OrderViewRepository;
// product module removed: no ProductViewRepository
import net.ijupiter.trading.core.query.repositories.TradeViewRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * 成交记录事件处理器
 * 
 * @author ijupiter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TradeEventHandler {
    
    private final TradeViewRepository tradeViewRepository;
    private final OrderViewRepository orderViewRepository;
    private final AccountViewRepository accountViewRepository;
    
    @EventHandler
    public void on(TradeExecutedEvent event) {
        log.debug("处理成交事件: {}", event.getTradeId());
        
        // 获取订单信息
        orderViewRepository.findById(event.getOrderId())
                .ifPresent(orderView -> {
                        // 使用订单中的产品信息（product 模块已删除）
                        String productCode = orderView.getProductCode();
                        String productName = orderView.getProductName();
                        // 确定客户ID和账户ID
                        String customerId = orderView.getCustomerId();
                        String accountId = orderView.getAccountId();

                        TradeView tradeView = TradeView.builder()
                            .tradeId(event.getTradeId())
                            .orderId(event.getOrderId())
                            .customerId(customerId)
                            .accountId(accountId)
                            .productCode(productCode)
                            .productName(productName)
                            .quantity(event.getQuantity())
                            .price(event.getPrice())
                            .amount(event.getAmount())
                            .side(orderView.getOrderSide())
                            .market(null)
                            .tradeTime(event.getTradeTime())
                            .tradeNo(generateTradeNo(event.getTradeId()))
                            .createTime(event.getTradeTime())
                            .build();

                        tradeViewRepository.save(tradeView);
                });
    }
    
    private String generateTradeNo(String tradeId) {
        // 简单的成交编号生成逻辑
        return "TRD" + System.currentTimeMillis() + tradeId.substring(Math.max(0, tradeId.length() - 6));
    }
}