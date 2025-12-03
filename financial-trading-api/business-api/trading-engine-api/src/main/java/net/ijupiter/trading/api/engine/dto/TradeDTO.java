package net.ijupiter.trading.api.engine.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeDTO {

    private String tradeId;
    private String buyOrderId;
    private String sellOrderId;
    private String productId;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal amount;
    private LocalDateTime tradeTime;

}