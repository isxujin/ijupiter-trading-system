package net.ijupiter.trading.common.constants;

import java.time.format.DateTimeFormatter;

/**
 * 通用常量类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public class CommonConstants {
    
    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    
    /**
     * 最大分页大小
     */
    public static final int MAX_PAGE_SIZE = 100;
    
    /**
     * 默认密码长度
     */
    public static final int DEFAULT_PASSWORD_LENGTH = 8;
    
    /**
     * 最大密码长度
     */
    public static final int MAX_PASSWORD_LENGTH = 20;
    
    /**
     * 最小密码长度
     */
    public static final int MIN_PASSWORD_LENGTH = 6;
    
    /**
     * 默认状态码
     */
    public static final String DEFAULT_STATUS_CODE = "SUCCESS";
    
    /**
     * 默认成功消息
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    
    /**
     * 默认失败消息
     */
    public static final String DEFAULT_FAILURE_MESSAGE = "操作失败";

    /**
     * 日期时间格式化器
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 构造函数私有化，防止实例化
     */
    private CommonConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}