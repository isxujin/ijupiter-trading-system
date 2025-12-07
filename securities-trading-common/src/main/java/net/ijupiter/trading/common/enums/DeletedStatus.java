package net.ijupiter.trading.common.enums;

/**
 * 删除状态枚举
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public enum DeletedStatus {
    
    /**
     * 未删除
     */
    NOT_DELETED(0, "未删除"),
    
    /**
     * 已删除
     */
    DELETED(1, "已删除");
    
    private final Integer code;
    private final String description;
    
    DeletedStatus(Integer code, String description) {
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
    public static DeletedStatus fromCode(Integer code) {
        for (DeletedStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown deleted status code: " + code);
    }
}