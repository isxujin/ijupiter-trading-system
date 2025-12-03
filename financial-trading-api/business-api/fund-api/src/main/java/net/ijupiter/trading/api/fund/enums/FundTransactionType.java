package net.ijupiter.trading.api.fund.enums;

/**
 * 资金交易类型枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum FundTransactionType {
    /**
     * 入金
     */
    DEPOSIT("入金"),
    
    /**
     * 出金
     */
    WITHDRAW("出金"),
    
    /**
     * 冻结
     */
    FREEZE("冻结"),
    
    /**
     * 解冻
     */
    UNFREEZE("解冻");
    
    private final String description;
    
    FundTransactionType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}