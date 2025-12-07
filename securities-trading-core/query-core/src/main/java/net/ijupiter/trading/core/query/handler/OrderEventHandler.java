package net.ijupiter.trading.core.query.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.core.query.model.OrderView;
import net.ijupiter.trading.core.query.repositories.AccountViewRepository;
import net.ijupiter.trading.core.query.repositories.OrderViewRepository;
import net.ijupiter.trading.api.engine.events.OrderCancelledEvent;
import net.ijupiter.trading.api.engine.events.OrderCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 订单事件处理器
 * 
 * @author ijupiter
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventHandler {
    
    private final OrderViewRepository orderViewRepository;
    private final AccountViewRepository accountViewRepository;
    
    @EventHandler
    public void on(OrderCreatedEvent event) {
        log.debug("处理订单创建事件: {}", event.getOrderId());
        
        // 获取账户信息，产品信息已不再从 product 模块获取
        accountViewRepository.findById(event.getAccountId())
            .ifPresent(account -> {
                String productCode = event.getProductId();
                String productName = "";

                OrderView orderView = OrderView.builder()
                    .orderId(event.getOrderId())
                    .customerId(account.getCustomerId())
                    .accountId(event.getAccountId())
                    .orderNo(generateOrderNo(event.getOrderId()))
                    .productCode(productCode)
                    .productName(productName)
                    .orderType(event.getType().name())
                    .orderSide(event.getSide().name())
                    .status(event.getStatus().name())
                    .price(event.getPrice())
                    .quantity(event.getQuantity())
                    .executedQuantity(BigDecimal.ZERO)
                    .executedAmount(BigDecimal.ZERO)
                    .avgPrice(BigDecimal.ZERO)
                    .amount(event.getPrice().multiply(event.getQuantity()))
                    .orderTime(event.getCreateTime())
                    .createTime(event.getCreateTime())
                    .updateTime(event.getCreateTime())
                    .build();

                orderViewRepository.save(orderView);
            });
    }
    
    @EventHandler
    public void on(OrderCancelledEvent event) {
        log.debug("处理订单取消事件: {}", event.getOrderId());
        
        orderViewRepository.findById(event.getOrderId())
                .ifPresent(orderView -> {
                    orderView.setStatus("CANCELLED");
                    orderView.setCancelTime(event.getCancelTime());
                    orderView.setUpdateTime(event.getCancelTime());
                    orderViewRepository.save(orderView);
                });
    }
    
//    @EventHandler
//    public void on(OrderPartiallyFilledEvent event) {
//        log.debug("处理订单部分成交事件: {}", event.getOrderId());
//
//        orderViewRepository.findById(event.getOrderId())
//                .ifPresent(orderView -> {
//                    // 更新成交数量和成交金额
//                    BigDecimal newExecutedQuantity = orderView.getExecutedQuantity().add(event.getFilledQuantity());
//                    BigDecimal newExecutedAmount = orderView.getExecutedAmount().add(event.getFilledAmount());
//
//                    orderView.setExecutedQuantity(newExecutedQuantity);
//                    orderView.setExecutedAmount(newExecutedAmount);
//                    orderView.setAvgPrice(newExecutedAmount.divide(newExecutedQuantity, 4, BigDecimal.ROUND_HALF_UP));
//                    orderView.setStatus("PARTIALLY_FILLED");
//                    orderView.setExecuteTime(event.getFillTime());
//                    orderView.setUpdateTime(event.getFillTime());
//                    orderViewRepository.save(orderView);
//                });
//    }
//
//    @EventHandler
//    public void on(OrderFullyFilledEvent event) {
//        log.debug("处理订单完全成交事件: {}", event.getOrderId());
//
//        orderViewRepository.findById(event.getOrderId())
//                .ifPresent(orderView -> {
//                    orderView.setExecutedQuantity(orderView.getQuantity());
//                    orderView.setExecutedAmount(orderView.getAmount());
//                    orderView.setAvgPrice(orderView.getPrice());
//                    orderView.setStatus("FILLED");
//                    orderView.setExecuteTime(event.getFillTime());
//                    orderView.setUpdateTime(event.getFillTime());
//                    orderViewRepository.save(orderView);
//                });
//    }
    
    private String generateOrderNo(String orderId) {
        // 简单的订单编号生成逻辑
        return "ORD" + System.currentTimeMillis() + orderId.substring(Math.max(0, orderId.length() - 6));
    }
}