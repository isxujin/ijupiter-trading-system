package net.ijupiter.trading.core.query.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.engine.events.TradeExecutedEvent;
import net.ijupiter.trading.core.query.model.TradeView;
import net.ijupiter.trading.core.query.repository.AccountViewRepository;
import net.ijupiter.trading.core.query.repository.OrderViewRepository;
import net.ijupiter.trading.core.query.repository.ProductViewRepository;
import net.ijupiter.trading.core.query.repository.TradeViewRepository;
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
    private final ProductViewRepository productViewRepository;
    private final AccountViewRepository accountViewRepository;
    
    @EventHandler
    public void on(TradeExecutedEvent event) {
        log.debug("处理成交事件: {}", event.getTradeId());
        
        // 获取订单信息
        orderViewRepository.findById(event.getOrderId())
                .ifPresent(orderView -> {
                    // 获取产品信息
                    productViewRepository.findById(event.getProductId())
                            .ifPresent(productView -> {
                                // 确定客户ID和账户ID
                                String customerId = orderView.getCustomerId();
                                String accountId = orderView.getAccountId();
                                
                                TradeView tradeView = TradeView.builder()
                                        .tradeId(event.getTradeId())
                                        .orderId(event.getOrderId())
                                        .customerId(customerId)
                                        .accountId(accountId)
                                        .productCode(productView.getProductCode())
                                        .productName(productView.getProductName())
                                        .quantity(event.getQuantity())
                                        .price(event.getPrice())
                                        .amount(event.getAmount())
                                        .side(orderView.getOrderSide())
                                        .market(productView.getMarket())
                                        .tradeTime(event.getTradeTime())
                                        .tradeNo(generateTradeNo(event.getTradeId()))
                                        .createTime(event.getTradeTime())
                                        .build();
                                        
                                tradeViewRepository.save(tradeView);
                            });
                });
    }
    
    private String generateTradeNo(String tradeId) {
        // 简单的成交编号生成逻辑
        return "TRD" + System.currentTimeMillis() + tradeId.substring(Math.max(0, tradeId.length() - 6));
    }
}