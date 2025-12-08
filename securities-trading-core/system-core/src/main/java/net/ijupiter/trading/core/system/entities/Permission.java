package net.ijupiter.trading.core.system.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统权限实体
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "syst_permission")
public class Permission extends BaseEntity<Permission> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限编码，用于业务标识
     */
    @Column(name = "permission_code", length = 128, nullable = false, unique = true)
    private String permissionCode;

    /**
     * 权限名称
     */
    @Column(name = "permission_name", length = 128, nullable = false)
    private String permissionName;

    /**
     * 权限描述
     */
    @Column(name = "description", length = 256)
    private String description;

    /**
     * 权限类型（menu：菜单，button：按钮，api：接口）
     */
    @Column(name = "permission_type", length = 32, nullable = false)
    private String permissionType;

    /**
     * 父权限ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 权限路径（用于前端路由或后端API路径）
     */
    @Column(name = "path", length = 256)
    private String path;

    /**
     * 权限图标
     */
    @Column(name = "icon", length = 128)
    private String icon;

    /**
     * 排序号
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * 权限状态（0：禁用，1：启用）
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 备注
     */
    @Column(name = "remark", length = 512)
    private String remark;

    /**
     * 拥有此权限的角色
     */
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();
}