package net.ijupiter.trading.common.exceptions;

/**
 * 数据访问异常
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public class DataAccessException extends SystemException {

    /**
     * 默认数据访问异常码
     */
    public static final String DEFAULT_DATA_ACCESS_ERROR_CODE = "DATA_ACCESS_ERROR";

    /**
     * 构造函数
     * 
     * @param message 错误消息
     */
    public DataAccessException(String message) {
        super(DEFAULT_DATA_ACCESS_ERROR_CODE, message);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     */
    public DataAccessException(String code, String message) {
        super(code, message);
    }

    /**
     * 构造函数
     * 
     * @param message 错误消息
     * @param cause 原因异常
     */
    public DataAccessException(String message, Throwable cause) {
        super(DEFAULT_DATA_ACCESS_ERROR_CODE, message, cause);
    }

    /**
     * 构造函数
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param cause 原因异常
     */
    public DataAccessException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}