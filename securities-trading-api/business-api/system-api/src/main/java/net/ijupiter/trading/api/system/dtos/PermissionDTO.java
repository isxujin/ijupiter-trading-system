package net.ijupiter.trading.api.system.dtos;

import lombok.*;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class PermissionDTO extends BaseDTO<PermissionDTO> {

    /**
     * 权限编码，用于业务标识
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限类型（menu：菜单，button：按钮，api：接口）
     */
    private String permissionType;

    /**
     * 父权限ID
     */
    private Long parentId;

    /**
     * 权限路径（用于前端路由或后端API路径）
     */
    private String path;

    /**
     * 权限图标
     */
    private String icon;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 权限状态（0：禁用，1：启用）
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
     * 子权限列表（用于构建树形结构）
     */
    private List<PermissionDTO> children;

    /**
     * 是否为叶子节点
     */
    private Boolean isLeaf;

    /**
     * 权限级别（根节点为0，依次递增）
     */
    private Integer level;

    /**
     * 关联的角色数量
     */
    private Integer roleCount;

    /**
     * 是否显示（对于菜单权限，控制是否在菜单中显示）
     */
    private Boolean visible;

    /**
     * 是否缓存（对于菜单权限，控制是否缓存路由组件）
     */
    private Boolean keepAlive;

    /**
     * 是否外链（对于菜单权限，判断是否为外链）
     */
    private Boolean isExternal;
}