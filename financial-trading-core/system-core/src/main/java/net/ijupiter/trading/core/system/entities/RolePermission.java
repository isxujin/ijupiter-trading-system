package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 角色权限关联实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sys_role_permission")
@Comment("角色权限关联表")
public class RolePermission {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment("主键ID")
    private Long id;

    /**
     * 角色ID
     */
    @Column(name = "role_id", length = 32, nullable = false)
    @Comment("角色ID")
    private String roleId;

    /**
     * 权限ID
     */
    @Column(name = "permission_id", length = 32, nullable = false)
    @Comment("权限ID")
    private String permissionId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Comment("创建时间")
    private LocalDateTime createTime;
}