package net.ijupiter.trading.web.common.models;

import lombok.Data;

/**
 * 统一返回结果
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
public class Result<T> {

    protected static final int SUCCESS_CODE = 0;
    protected static final int FAIL_CODE = -1;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功返回
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "操作成功", null);
    }

    /**
     * 成功返回数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "操作成功", data);
    }

    /**
     * 成功返回消息和数据
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(SUCCESS_CODE, message, data);
    }

    /**
     * 失败返回
     */
    public static <T> Result<T> fail() {
        return new Result<>(FAIL_CODE, "操作失败", null);
    }

    /**
     * 失败返回消息
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(FAIL_CODE, message, null);
    }

    /**
     * 失败返回码和消息
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return SUCCESS_CODE == this.code;
    }

    /**
     * 判断是否失败
     */
    public boolean isFail() {
        return !isSuccess();
    }
}