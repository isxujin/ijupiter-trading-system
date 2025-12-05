package net.ijupiter.trading.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
public class DateUtils {
    
    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 默认日期时间格式化器
     */
    public static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = 
            DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
    
    /**
     * 格式化日期时间为字符串
     * 
     * @param dateTime 日期时间
     * @return 格式化后的字符串
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DEFAULT_DATETIME_FORMATTER);
    }
    
    /**
     * 格式化日期时间为字符串
     * 
     * @param dateTime 日期时间
     * @param pattern 格式模式
     * @return 格式化后的字符串
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 解析字符串为日期时间
     * 
     * @param dateTimeStr 日期时间字符串
     * @return 日期时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DEFAULT_DATETIME_FORMATTER);
    }
    
    /**
     * 解析字符串为日期时间
     * 
     * @param dateTimeStr 日期时间字符串
     * @param pattern 格式模式
     * @return 日期时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }
}