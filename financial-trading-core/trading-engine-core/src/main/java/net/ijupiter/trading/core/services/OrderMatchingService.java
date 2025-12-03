package net.ijupiter.trading.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ijupiter.trading.api.engine.enums.OrderSide;
import net.ijupiter.trading.api.engine.enums.OrderStatus;
import net.ijupiter.trading.api.engine.enums.OrderType;
import net.ijupiter.trading.api.engine.events.OrderCancelledEvent;
import net.ijupiter.trading.api.engine.events.TradeExecutedEvent;
import net.ijupiter.trading.core.entities.OrderEntity;
import net.ijupiter.trading.core.repositories.OrderRepository;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * 订单匹配服务
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMatchingService {

    private final OrderRepository orderRepository;
    
    @Autowired
    private EventBus eventBus;

    /**
     * 匹配订单
     * 
     * @param productId 产品ID
     */
    @Transactional
    public void matchOrders(String productId) {
        log.info("开始匹配订单: productId={}", productId);

        // 获取买盘和卖盘订单
        List<OrderEntity> buyOrders = orderRepository.findByProductIdAndSideAndStatusOrderByCreateTimeAsc(
                productId, OrderSide.BUY, OrderStatus.CONFIRMED);
        List<OrderEntity> sellOrders = orderRepository.findByProductIdAndSideAndStatusOrderByCreateTimeAsc(
                productId, OrderSide.SELL, OrderStatus.CONFIRMED);

        // 对买盘按价格降序排列，卖盘按价格升序排列
        buyOrders.sort(Comparator.comparing(OrderEntity::getPrice).reversed());
        sellOrders.sort(Comparator.comparing(OrderEntity::getPrice));

        int buyIndex = 0;
        int sellIndex = 0;

        while (buyIndex < buyOrders.size() && sellIndex < sellOrders.size()) {
            OrderEntity buyOrder = buyOrders.get(buyIndex);
            OrderEntity sellOrder = sellOrders.get(sellIndex);

            // 检查价格是否匹配
            if (buyOrder.getPrice().compareTo(sellOrder.getPrice()) < 0) {
                // 买价低于卖价，无法匹配
                break;
            }

            // 确定成交价格：使用较早提交的订单价格
            BigDecimal tradePrice;
            if (buyOrder.getCreateTime().isBefore(sellOrder.getCreateTime())) {
                tradePrice = buyOrder.getPrice();
            } else {
                tradePrice = sellOrder.getPrice();
            }

            // 确定成交数量
            BigDecimal buyRemaining = buyOrder.getRemainingQuantity();
            BigDecimal sellRemaining = sellOrder.getRemainingQuantity();
            BigDecimal tradeQuantity = buyRemaining.min(sellRemaining);

            // 执行交易
            executeTrade(buyOrder, sellOrder, tradePrice, tradeQuantity);

            // 更新索引
            if (buyRemaining.compareTo(tradeQuantity) == 0) {
                buyIndex++;
            }
            if (sellRemaining.compareTo(tradeQuantity) == 0) {
                sellIndex++;
            }
        }

        log.info("订单匹配完成: productId={}", productId);
    }

    /**
     * 执行交易
     * 
     * @param buyOrder 买单
     * @param sellOrder 卖单
     * @param tradePrice 成交价格
     * @param tradeQuantity 成交数量
     */
    private void executeTrade(OrderEntity buyOrder, OrderEntity sellOrder, 
                              BigDecimal tradePrice, BigDecimal tradeQuantity) {
        log.info("执行交易: buyOrderId={}, sellOrderId={}, price={}, quantity={}", 
                buyOrder.getOrderId(), sellOrder.getOrderId(), tradePrice, tradeQuantity);

        // 创建交易ID
        String tradeId = UUID.randomUUID().toString();
        
        // 计算成交金额
        BigDecimal tradeAmount = tradePrice.multiply(tradeQuantity);

        // 发送交易执行事件
        TradeExecutedEvent tradeEvent = new TradeExecutedEvent(
                tradeId,
                buyOrder.getOrderId(),
                buyOrder.getOrderId(),
                sellOrder.getOrderId(),
                buyOrder.getProductId(),
                tradePrice,
                tradeQuantity,
                tradeAmount
        );
        eventBus.publish(GenericEventMessage.asEventMessage(tradeEvent));

        // 更新买单成交信息
        updateOrderAfterTrade(buyOrder, tradeQuantity, tradePrice);

        // 更新卖单成交信息
        updateOrderAfterTrade(sellOrder, tradeQuantity, tradePrice);

        // 检查并处理市价单
        processMarketOrderAfterTrade(buyOrder, tradeQuantity);
        processMarketOrderAfterTrade(sellOrder, tradeQuantity);
    }

    /**
     * 交易后更新订单状态
     * 
     * @param order 订单
     * @param tradeQuantity 成交数量
     * @param tradePrice 成交价格
     */
    private void updateOrderAfterTrade(OrderEntity order, BigDecimal tradeQuantity, BigDecimal tradePrice) {
        BigDecimal newFilledQuantity = order.getFilledQuantity().add(tradeQuantity);
        BigDecimal newRemainingQuantity = order.getRemainingQuantity().subtract(tradeQuantity);
        
        // 计算新的平均价格
        BigDecimal filledAmount = order.getFilledQuantity().multiply(order.getAveragePrice());
        BigDecimal tradeAmount = tradeQuantity.multiply(tradePrice);
        BigDecimal newFilledAmount = filledAmount.add(tradeAmount);
        BigDecimal newAveragePrice;
        if (newFilledQuantity.compareTo(BigDecimal.ZERO) > 0) {
            newAveragePrice = newFilledAmount.divide(newFilledQuantity, 4, BigDecimal.ROUND_HALF_UP);
        } else {
            newAveragePrice = BigDecimal.ZERO;
        }
        
        order.setFilledQuantity(newFilledQuantity);
        order.setRemainingQuantity(newRemainingQuantity);
        order.setAveragePrice(newAveragePrice);
        
        // 更新订单状态
        if (newRemainingQuantity.compareTo(BigDecimal.ZERO) == 0) {
            order.setStatus(OrderStatus.FILLED);
        } else if (newFilledQuantity.compareTo(BigDecimal.ZERO) > 0) {
            order.setStatus(OrderStatus.PARTIALLY_FILLED);
        } else {
            order.setStatus(OrderStatus.CONFIRMED);
        }
        
        orderRepository.save(order);
    }

    /**
     * 处理市价单的后续状态
     * 
     * @param order 订单
     * @param tradeQuantity 成交数量
     */
    private void processMarketOrderAfterTrade(OrderEntity order, BigDecimal tradeQuantity) {
        if (OrderType.MARKET.equals(order.getType())) {
            BigDecimal remainingQuantity = order.getRemainingQuantity();
            
            // 如果市价单仍有剩余数量且没有足够的对手盘，则取消剩余部分
            if (remainingQuantity.compareTo(BigDecimal.ZERO) > 0) {
                log.info("取消市价单剩余部分: orderId={}, remainingQuantity={}", 
                        order.getOrderId(), remainingQuantity);
                
                OrderCancelledEvent cancelEvent = new OrderCancelledEvent(
                        order.getOrderId(),
                        "市价单部分成交，无足够对手盘"
                );
                eventBus.publish(GenericEventMessage.asEventMessage(cancelEvent));
                
                order.setStatus(OrderStatus.PARTIALLY_FILLED);
                orderRepository.save(order);
            }
        }
    }

    /**
     * 启动订单匹配
     * 
     * @param productId 产品ID
     */
    @Transactional
    public void startMatching(String productId) {
        log.info("启动订单匹配: productId={}", productId);

        // 获取待确认的订单，将其状态改为已确认
        List<OrderEntity> pendingOrders = orderRepository.findByProductIdAndStatus(
                productId, OrderStatus.PENDING);
        
        for (OrderEntity order : pendingOrders) {
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
        }

        // 执行匹配
        matchOrders(productId);
    }
}