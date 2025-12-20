package net.ijupiter.trading.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统环境信息声明类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemEnv extends BaseDTO<SystemEnv> {
    /**
     * 系统名称
     */
    private static String systemName;
    /**
     * 系统版本
     */
    private static String systemVersion;
    /**
     * 系统标题
     */
    private static String systemTitle;
}
