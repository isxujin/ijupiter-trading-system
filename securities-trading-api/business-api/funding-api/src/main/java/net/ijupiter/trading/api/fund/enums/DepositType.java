package net.ijupiter.trading.api.fund.enums;

/**
 * 入金类型枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum DepositType {
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
     * 充值卡
     */
    RECHARGE_CARD("充值卡"),
    
    /**
     * 内部转账
     */
    INTERNAL_TRANSFER("内部转账"),
    
    /**
     * 退款
     */
    REFUND("退款"),
    
    /**
     * 奖金
     */
    BONUS("奖金");
    
    private final String description;
    
    DepositType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}