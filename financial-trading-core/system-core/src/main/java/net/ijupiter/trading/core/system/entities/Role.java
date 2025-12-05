package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;
import org.hibernate.annotations.Comment;



/**
 * 角色实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_role")
@Comment("角色表")
public class Role extends BaseEntity<Role> {
    /**
     * 角色编码
     */
    @Column(name = "role_code", length = 64, nullable = false)
    @Comment("角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @Column(name = "role_name", length = 64, nullable = false)
    @Comment("角色名称")
    private String roleName;

    /**
     * 角色描述
     */
    @Column(name = "description", length = 256)
    @Comment("角色描述")
    private String description;

    /**
     * 状态（0-禁用，1-启用）
     */
    @Column(name = "status")
    @Comment("状态（0-禁用，1-启用）")
    private Integer status;

    /**
     * 是否系统内置角色
     */
    @Column(name = "is_system")
    @Comment("是否系统内置角色")
    private Boolean isSystem;
}