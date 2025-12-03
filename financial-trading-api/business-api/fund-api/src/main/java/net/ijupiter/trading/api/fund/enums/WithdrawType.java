package net.ijupiter.trading.api.fund.enums;

/**
 * 出金类型枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum WithdrawType {
    /**
     * 银行转账
     */
    BANK_TRANSFER("银行转账"),
    
    /**
     * 在线支付
     */
    ONLINE_PAYMENT("在线支付"),
    
    /**
     * 第三方支付
     */
    THIRD_PARTY("第三方支付"),
    
    /**
     * 内部转账
     */
    INTERNAL_TRANSFER("内部转账"),
    
    /**
     * 手续费
     */
    FEE("手续费"),
    
    /**
     * 交易成交
     */
    TRADE("交易成交"),
    
    /**
     * 冻结解冻
     */
    UNFREEZE("冻结解冻");
    
    private final String description;
    
    WithdrawType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}