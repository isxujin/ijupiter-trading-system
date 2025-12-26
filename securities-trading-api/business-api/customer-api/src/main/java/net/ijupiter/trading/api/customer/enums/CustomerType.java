package net.ijupiter.trading.api.customer.enums;

/**
 * 客户类型枚举
 */
public enum CustomerType {
    INDIVIDUAL(1, "个人"),
    INSTITUTION(2, "机构");
    
    private final int code;
    private final String description;
    
    CustomerType(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static CustomerType fromCode(int code) {
        for (CustomerType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid customer type code: " + code);
    }
}