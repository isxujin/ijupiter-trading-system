package net.ijupiter.trading.api.customer.enums;

/**
 * 客户状态枚举
 */
public enum CustomerStatus {
    NORMAL(1, "正常"),
    FROZEN(2, "冻结"),
    CLOSED(3, "注销");
    
    private final int code;
    private final String description;
    
    CustomerStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static CustomerStatus fromCode(int code) {
        for (CustomerStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid customer status code: " + code);
    }
}