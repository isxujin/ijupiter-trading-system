package net.ijupiter.trading.common.enums;

/**
 * 系统状态枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum SystemStatus {
    
    /**
     * 启用
     */
    ENABLED(1, "启用"),
    
    /**
     * 禁用
     */
    DISABLED(0, "禁用");
    
    private final Integer code;
    private final String description;
    
    SystemStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * 根据代码获取状态
     * 
     * @param code 状态代码
     * @return 状态枚举
     */
    public static SystemStatus fromCode(Integer code) {
        for (SystemStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status code: " + code);
    }
}