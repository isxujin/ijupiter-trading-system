package net.ijupiter.trading.api.settlement.enums;

/**
 * 结算状态枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum SettlementStatus {
    /**
     * 待处理
     */
    PENDING("待处理"),
    
    /**
     * 处理中
     */
    IN_PROGRESS("处理中"),
    
    /**
     * 已完成
     */
    COMPLETED("已完成"),
    
    /**
     * 已失败
     */
    FAILED("已失败"),
    
    /**
     * 已取消
     */
    CANCELLED("已取消");
    
    private final String description;
    
    SettlementStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}