package net.ijupiter.trading.api.account.enums;

/**
 * 账户类型枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum AccountType {
    /**
     * 证券账户
     */
    SECURITY("证券账户"),
    
    /**
     * 资金账户
     */
    CASH("资金账户"),
    
    /**
     * 综合账户（证券+资金）
     */
    COMPREHENSIVE("综合账户");
    
    private final String description;
    
    AccountType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}