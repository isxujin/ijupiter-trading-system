package net.ijupiter.trading.api.system.dtos;

import lombok.*;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class UserDTO extends BaseDTO<UserDTO> {

    /**
     * 用户编号，用于业务标识
     */
    private String userCode;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（在传输时通常为null或加密字符串）
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 用户角色ID列表（用于分配角色时使用）
     */
    private List<Long> roleIds;

    /**
     * 用户角色名称列表（用于展示）
     */
    private List<String> roleNames;

    /**
     * 是否拥有管理员权限
     */
    private Boolean isAdmin;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 密码是否已加密
     */
    private Boolean passwordEncrypted;
}