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
public class SystemEnvironment extends BaseDTO<SystemEnvironment> {
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 系统版本
     */
    private String systemVersion;
    /**
     * 系统标题
     */
    private String systemTitle;

    /**
     * 当前登录用户名称
     */
    private String currentUserName;
}
