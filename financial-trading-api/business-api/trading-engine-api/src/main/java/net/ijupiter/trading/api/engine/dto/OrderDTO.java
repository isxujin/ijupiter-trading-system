package net.ijupiter.trading.api.engine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ijupiter.trading.api.engine.enums.OrderSide;
import net.ijupiter.trading.api.engine.enums.OrderStatus;
import net.ijupiter.trading.api.engine.enums.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime cancelTime;
    private String cancelReason;

    /**
     * 计算成交金额
     * 
     * @return 成交金额
     */
    public BigDecimal getFilledAmount() {
        if (filledQuantity == null || averagePrice == null) {
            return BigDecimal.ZERO;
        }
        return filledQuantity.multiply(averagePrice);
    }

    /**
     * 是否已全部成交
     * 
     * @return 是否全部成交
     */
    public boolean isFullyFilled() {
        return OrderStatus.FILLED.equals(status) || 
               (filledQuantity != null && originalQuantity != null && 
                filledQuantity.compareTo(originalQuantity) >= 0);
    }

    /**
     * 是否部分成交
     * 
     * @return 是否部分成交
     */
    public boolean isPartiallyFilled() {
        return filledQuantity != null && originalQuantity != null && 
               filledQuantity.compareTo(BigDecimal.ZERO) > 0 && 
               filledQuantity.compareTo(originalQuantity) < 0;
    }
}