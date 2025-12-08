package net.ijupiter.trading.api.system.dtos;

import lombok.*;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class RoleDTO extends BaseDTO<RoleDTO> {

    /**
     * 角色编码，用于业务标识
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 排序号
     */
    private Integer sortOrder;

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
     * 角色权限ID列表（用于分配权限时使用）
     */
    private List<Long> permissionIds;

    /**
     * 角色权限名称列表（用于展示）
     */
    private List<String> permissionNames;

    /**
     * 角色关联的用户数量
     */
    private Integer userCount;

    /**
     * 是否为系统角色（不可删除）
     */
    private Boolean isSystemRole;

    /**
     * 角色类型（admin：管理员角色，user：普通用户角色）
     */
    private String roleType;
}