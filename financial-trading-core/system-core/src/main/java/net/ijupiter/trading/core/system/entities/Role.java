package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 角色实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sys_role")
@Comment("角色表")
public class Role {

    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id", length = 32)
    @Comment("角色ID")
    private String roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name", length = 64, nullable = false)
    @Comment("角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @Column(name = "role_code", length = 64, nullable = false)
    @Comment("角色编码")
    private String roleCode;

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

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Comment("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @Comment("更新时间")
    private LocalDateTime updateTime;
}