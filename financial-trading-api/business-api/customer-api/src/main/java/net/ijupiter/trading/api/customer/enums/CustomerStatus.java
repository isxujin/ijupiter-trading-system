package net.ijupiter.trading.api.customer.enums;

/**
 * 客户状态枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum CustomerStatus {
    /**
     * 正常
     */
    NORMAL("正常"),
    
    /**
     * 冻结
     */
    FROZEN("冻结"),
    
    /**
     * 注销
     */
    CANCELLED("注销");
    
    private final String description;
    
    CustomerStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}