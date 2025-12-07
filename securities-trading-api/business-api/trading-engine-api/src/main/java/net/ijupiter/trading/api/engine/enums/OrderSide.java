package net.ijupiter.trading.api.engine.enums;

/**
 * 订单方向枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum OrderSide {
    /**
     * 买入
     */
    BUY("买入"),
    
    /**
     * 卖出
     */
    SELL("卖出");
    
    private final String description;
    
    OrderSide(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}