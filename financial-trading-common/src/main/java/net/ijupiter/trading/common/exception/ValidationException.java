package net.ijupiter.trading.common.exception;

/**
 * 验证异常
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public class ValidationException extends BaseException {

    /**
     * 默认验证异常码
     */
    public static final String DEFAULT_VALIDATION_ERROR_CODE = "VALIDATION_ERROR";

    /**
     * 构造函数
     */
    public ValidationException() {
        super(DEFAULT_VALIDATION_ERROR_CODE);
    }

    /**
     * 构造函数
     * 
     * @param message 错误消息
     */
    public ValidationException(String message) {
        super(DEFAULT_VALIDATION_ERROR_CODE, message);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     */
    public ValidationException(String code, String message) {
        super(code, message);
    }

    /**
     * 构造函数
     * 
     * @param message 错误消息
     * @param cause 原因异常
     */
    public ValidationException(String message, Throwable cause) {
        super(DEFAULT_VALIDATION_ERROR_CODE, message, cause);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param cause 原因异常
     */
    public ValidationException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param detail 详细描述
     */
    public ValidationException(String code, String message, String detail) {
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
    public ValidationException(String code, String message, String detail, Throwable cause) {
        super(code, message, detail, cause);
    }
}