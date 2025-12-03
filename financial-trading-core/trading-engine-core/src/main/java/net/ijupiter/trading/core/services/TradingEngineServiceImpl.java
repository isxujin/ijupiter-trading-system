package net.ijupiter.trading.core.services;

import net.ijupiter.trading.api.engine.enums.OrderSide;
import net.ijupiter.trading.core.entities.OrderEntity;
import net.ijupiter.trading.core.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TradingEngineServiceImpl {

    // 构造器注入或@Resource注入均可
    private final OrderRepository orderRepository;

    public TradingEngineServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 业务方法：替代原有的 findByProductIdAndSideAndPriceCondition 调用
    public List<OrderEntity> queryOrders(String productId, OrderSide side, BigDecimal price, boolean isAbove) {
        if (isAbove) {
            // 价格 >= 目标价
            return orderRepository.findByProductIdAndSideAndPriceGreaterThanEqual(productId, side, price);
        } else {
            // 价格 <= 目标价
            return orderRepository.findByProductIdAndSideAndPriceLessThanEqual(productId, side, price);
        }
    }

    // 其他业务逻辑...
}