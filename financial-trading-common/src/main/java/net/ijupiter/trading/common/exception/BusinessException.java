package net.ijupiter.trading.common.exception;

/**
 * 业务异常
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public class BusinessException extends BaseException {

    /**
     * 默认业务异常码
     */
    public static final String DEFAULT_BUSINESS_ERROR_CODE = "BUSINESS_ERROR";

    /**
     * 构造函数
     */
    public BusinessException() {
        super(DEFAULT_BUSINESS_ERROR_CODE);
    }

    /**
     * 构造函数
     * 
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(DEFAULT_BUSINESS_ERROR_CODE, message);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(String code, String message) {
        super(code, message);
    }

    /**
     * 构造函数
     * 
     * @param message 错误消息
     * @param cause 原因异常
     */
    public BusinessException(String message, Throwable cause) {
        super(DEFAULT_BUSINESS_ERROR_CODE, message, cause);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param cause 原因异常
     */
    public BusinessException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param detail 详细描述
     */
    public BusinessException(String code, String message, String detail) {
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
    public BusinessException(String code, String message, String detail, Throwable cause) {
        super(code, message, detail, cause);
    }
}