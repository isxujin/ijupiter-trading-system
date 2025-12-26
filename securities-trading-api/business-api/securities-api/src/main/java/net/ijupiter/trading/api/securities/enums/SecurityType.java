package net.ijupiter.trading.api.securities.enums;

/**
 * 证券类型枚举
 */
public enum SecurityType {
    STOCK(1, "股票"),
    FUND(2, "基金"),
    BOND(3, "债券"),
    DERIVATIVE(4, "衍生品");
    
    private final int code;
    private final String description;
    
    SecurityType(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static SecurityType fromCode(int code) {
        for (SecurityType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid security type code: " + code);
    }
}