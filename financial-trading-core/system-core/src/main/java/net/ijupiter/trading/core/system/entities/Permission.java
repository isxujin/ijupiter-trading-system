package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import net.ijupiter.trading.common.base.BaseEntityWithCustomId;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 权限实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sys_permission")
@Comment("权限表")
public class Permission  extends BaseEntityWithCustomId {
    /**
     * 权限ID
     */
    @Id
    @Column(name = "permission_id", length = 32)
    @Comment("权限ID")
    private String permissionId;

    /**
     * 权限名称
     */
    @Column(name = "permission_name", length = 64, nullable = false)
    @Comment("权限名称")
    private String permissionName;

    /**
     * 权限编码
     */
    @Column(name = "permission_code", length = 64, nullable = false)
    @Comment("权限编码")
    private String permissionCode;

    /**
     * 权限描述
     */
    @Column(name = "description", length = 256)
    @Comment("权限描述")
    private String description;

    /**
     * 权限类型（menu-菜单，button-按钮，api-接口）
     */
    @Column(name = "permission_type", length = 16)
    @Comment("权限类型（menu-菜单，button-按钮，api-接口）")
    private String permissionType;

    /**
     * 父权限ID
     */
    @Column(name = "parent_id", length = 32)
    @Comment("父权限ID")
    private String parentId;

    /**
     * 权限路径（菜单路径或API路径）
     */
    @Column(name = "permission_path", length = 256)
    @Comment("权限路径（菜单路径或API路径）")
    private String permissionPath;

    /**
     * 权限图标（仅菜单类型有效）
     */
    @Column(name = "icon", length = 64)
    @Comment("权限图标（仅菜单类型有效）")
    private String icon;

    /**
     * 排序号
     */
    @Column(name = "sort_order")
    @Comment("排序号")
    private Integer sortOrder;

    /**
     * 状态（0-禁用，1-启用）
     */
    @Column(name = "status")
    @Comment("状态（0-禁用，1-启用）")
    private Integer status;

}