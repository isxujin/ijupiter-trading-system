package net.ijupiter.trading.api.engine.enums;

/**
 * 订单类型枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum OrderType {
    /**
     * 限价单
     */
    LIMIT("限价单"),
    
    /**
     * 市价单
     */
    MARKET("市价单"),
    
    /**
     * 条件单
     */
    CONDITIONAL("条件单");
    
    private final String description;
    
    OrderType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}