package net.ijupiter.trading.common.exceptions;

import lombok.Getter;

import java.io.Serial;

/**
 * 系统异常基类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Getter
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    
    private final String code;

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     */
    public BaseException(String code, String message) {
        super(message);
        this.code = code;
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
    }
}