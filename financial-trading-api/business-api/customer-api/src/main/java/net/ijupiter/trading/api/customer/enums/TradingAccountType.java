package net.ijupiter.trading.api.customer.enums;

/**
 * 交易账户类型枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum TradingAccountType {
    /**
     * 股票账户
     */
    STOCK("股票账户"),
    
    /**
     * 期货账户
     */
    FUTURES("期货账户"),
    
    /**
     * 期权账户
     */
    OPTIONS("期权账户"),
    
    /**
     * 基金账户
     */
    FUND("基金账户"),
    
    /**
     * 债券账户
     */
    BOND("债券账户");
    
    private final String description;
    
    TradingAccountType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}