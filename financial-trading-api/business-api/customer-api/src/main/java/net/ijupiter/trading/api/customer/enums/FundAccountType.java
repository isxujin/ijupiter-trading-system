package net.ijupiter.trading.api.customer.enums;

/**
 * 资金账户类型枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum FundAccountType {
    /**
     * 主资金账户
     */
    MAIN("主资金账户"),
    
    /**
     * 保证金账户
     */
    MARGIN("保证金账户"),
    
    /**
     * 第三方存管账户
     */
    THIRD_PARTY("第三方存管账户");
    
    private final String description;
    
    FundAccountType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}