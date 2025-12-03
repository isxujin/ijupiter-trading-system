package net.ijupiter.trading.api.fund.enums;

/**
 * 资金账户状态枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum FundAccountStatus {
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
    
    FundAccountStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}