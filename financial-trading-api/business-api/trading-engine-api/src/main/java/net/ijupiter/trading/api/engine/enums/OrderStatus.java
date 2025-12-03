package net.ijupiter.trading.api.engine.enums;

/**
 * 订单状态枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum OrderStatus {
    /**
     * 待处理
     */
    PENDING("待处理"),
    
    /**
     * 已确认
     */
    CONFIRMED("已确认"),
    
    /**
     * 部分成交
     */
    PARTIALLY_FILLED("部分成交"),
    
    /**
     * 全部成交
     */
    FILLED("全部成交"),
    
    /**
     * 已取消
     */
    CANCELLED("已取消"),
    
    /**
     * 已拒绝
     */
    REJECTED("已拒绝");
    
    private final String description;
    
    OrderStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}