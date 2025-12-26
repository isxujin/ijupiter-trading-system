package net.ijupiter.trading.api.funding.enums;

/**
 * 账户状态枚举
 */
public enum AccountStatus {
    NORMAL(1, "正常"),
    FROZEN(2, "冻结"),
    CLOSED(3, "注销");
    
    private final int code;
    private final String description;
    
    AccountStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static AccountStatus fromCode(int code) {
        for (AccountStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid account status code: " + code);
    }
}