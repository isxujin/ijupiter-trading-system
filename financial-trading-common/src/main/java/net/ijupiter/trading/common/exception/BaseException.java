package net.ijupiter.trading.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统服务端异常基类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 详细描述
     */
    private String detail;

    /**
     * 构造函数
     */
    public BaseException() {
        super();
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     */
    public BaseException(String code) {
        this.code = code;
    }

    /**
     * 构造函数
     * 
     * @param message 错误消息
     */
    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param cause 原因异常
     */
    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param detail 详细描述
     */
    public BaseException(String code, String message, String detail) {
        super(message);
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param detail 详细描述
     * @param cause 原因异常
     */
    public BaseException(String code, String message, String detail, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.detail = detail;
    }
}