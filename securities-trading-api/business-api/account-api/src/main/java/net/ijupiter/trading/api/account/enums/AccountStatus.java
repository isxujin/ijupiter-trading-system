package net.ijupiter.trading.api.account.enums;

/**
 * 账户状态枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum AccountStatus {
    /**
     * 正常
     */
    NORMAL("正常"),
    
    /**
     * 冻结
     */
    FROZEN("冻结"),
    
    /**
     * 锁定
     */
    LOCKED("锁定"),
    
    /**
     * 已关闭
     */
    CLOSED("已关闭"),
    
    /**
     * 注销
     */
    CANCELLED("注销");
    
    private final String description;
    
    AccountStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}