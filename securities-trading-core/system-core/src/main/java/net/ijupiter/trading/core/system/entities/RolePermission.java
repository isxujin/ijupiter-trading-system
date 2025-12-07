package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;
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
public class RolePermission  extends BaseEntity<RolePermission> {
    /**
     * 角色ID
     */
    @Column(name = "role_id", length = 36, nullable = false)
    @Comment("角色ID")
    private String roleId;

    /**
     * 权限ID
     */
    @Column(name = "permission_id", length = 36, nullable = false)
    @Comment("权限ID")
    private String permissionId;

}