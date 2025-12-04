package net.ijupiter.trading.core.aggregates;

import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.engine.commands.CancelOrderCommand;
import net.ijupiter.trading.api.engine.commands.CreateOrderCommand;
import net.ijupiter.trading.api.engine.enums.OrderSide;
import net.ijupiter.trading.api.engine.enums.OrderStatus;
import net.ijupiter.trading.api.engine.enums.OrderType;
import net.ijupiter.trading.api.engine.events.OrderCancelledEvent;
import net.ijupiter.trading.api.engine.events.OrderCreatedEvent;
import net.ijupiter.trading.core.entities.OrderEntity;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

/**
 * 订单聚合
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Aggregate
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String accountId;
    private String productId;
    private OrderSide side;
    private OrderType type;
    private BigDecimal price;
    private BigDecimal originalQuantity;
    private BigDecimal filledQuantity;
    private BigDecimal remainingQuantity;
    private BigDecimal averagePrice;
    private OrderStatus status;

    /**
     * 创建订单命令处理器
     * 
     * @param command 创建订单命令
     */
    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        OrderCreatedEvent event = new OrderCreatedEvent(
                command.getOrderId(),
                command.getAccountId(),
                command.getProductId(),
                command.getSide(),
                command.getType(),
                command.getPrice(),
                command.getQuantity(),
                OrderStatus.PENDING
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 取消订单命令处理器
     * 
     * @param command 取消订单命令
     */
    @CommandHandler
    public void handle(CancelOrderCommand command) {
        if (!canCancel()) {
            throw new IllegalStateException("订单无法取消，当前状态：" + status);
        }

        OrderCancelledEvent event = new OrderCancelledEvent(
                command.getOrderId(),
                command.getReason() != null ? command.getReason() : "用户取消"
        );
        AggregateLifecycle.apply(event);
    }

    /**
     * 处理订单创建事件
     * 
     * @param event 订单创建事件
     */
    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.accountId = event.getAccountId();
        this.productId = event.getProductId();
        this.side = event.getSide();
        this.type = event.getType();
        this.price = event.getPrice();
        this.originalQuantity = event.getQuantity();
        this.filledQuantity = BigDecimal.ZERO;
        this.remainingQuantity = event.getQuantity();
        this.averagePrice = BigDecimal.ZERO;
        this.status = event.getStatus();
    }

    /**
     * 处理订单取消事件
     * 
     * @param event 订单取消事件
     */
    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        this.status = OrderStatus.CANCELLED;
    }

    /**
     * 判断订单是否可以取消
     * 
     * @return 是否可以取消
     */
    private boolean canCancel() {
        return OrderStatus.PENDING.equals(status) || 
               OrderStatus.CONFIRMED.equals(status) || 
               OrderStatus.PARTIALLY_FILLED.equals(status);
    }

    /**
     * 冻结资金
     * 
     * @param order 订单实体
     */
    private void freezeFunds(OrderEntity order) {
        if (OrderSide.BUY.equals(order.getSide())) {
            // 买入订单需要冻结资金
            BigDecimal frozenAmount = order.getPrice().multiply(order.getRemainingQuantity());
            // 调用资金服务冻结资金
        }
    }
}