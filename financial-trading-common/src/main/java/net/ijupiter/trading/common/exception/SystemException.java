package net.ijupiter.trading.common.exception;

/**
 * 系统异常
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public class SystemException extends BaseException {

    /**
     * 默认系统异常码
     */
    public static final String DEFAULT_SYSTEM_ERROR_CODE = "SYSTEM_ERROR";

    /**
     * 构造函数
     */
    public SystemException() {
        super(DEFAULT_SYSTEM_ERROR_CODE);
    }

    /**
     * 构造函数
     * 
     * @param message 错误消息
     */
    public SystemException(String message) {
        super(DEFAULT_SYSTEM_ERROR_CODE, message);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     */
    public SystemException(String code, String message) {
        super(code, message);
    }

    /**
     * 构造函数
     * 
     * @param message 错误消息
     * @param cause 原因异常
     */
    public SystemException(String message, Throwable cause) {
        super(DEFAULT_SYSTEM_ERROR_CODE, message, cause);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param cause 原因异常
     */
    public SystemException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param detail 详细描述
     */
    public SystemException(String code, String message, String detail) {
        super(code, message, detail);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param detail 详细描述
     * @param cause 原因异常
     */
    public SystemException(String code, String message, String detail, Throwable cause) {
        super(code, message, detail, cause);
    }
}