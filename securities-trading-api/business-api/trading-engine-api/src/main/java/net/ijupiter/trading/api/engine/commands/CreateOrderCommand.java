package net.ijupiter.trading.api.engine.commands;

import lombok.Getter;
import net.ijupiter.trading.api.engine.enums.OrderSide;
import net.ijupiter.trading.api.engine.enums.OrderType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 创建订单命令
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class CreateOrderCommand {

    @NotBlank
    private final String orderId;
    
    @NotBlank
    private final String accountId;
    
    @NotBlank
    private final String productId;
    
    @NotNull
    private final OrderSide side;
    
    @NotNull
    private final OrderType type;
    
    @Positive
    private final BigDecimal price;
    
    @Positive
    private final BigDecimal quantity;

    public CreateOrderCommand(String orderId, String accountId, String productId, 
                             OrderSide side, OrderType type, BigDecimal price, 
                             BigDecimal quantity) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.productId = productId;
        this.side = side;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }
}