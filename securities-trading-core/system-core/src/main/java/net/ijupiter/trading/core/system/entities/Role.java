package net.ijupiter.trading.core.system.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色实体
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
@Table(name = "syst_role")
public class Role extends BaseEntity<Role> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色编码，用于业务标识
     */
    @Column(name = "role_code", length = 64, nullable = false, unique = true)
    private String roleCode;

    /**
     * 角色名称
     */
    @Column(name = "role_name", length = 64, nullable = false)
    private String roleName;

    /**
     * 角色描述
     */
    @Column(name = "description", length = 256)
    private String description;

    /**
     * 角色状态（0：禁用，1：启用）
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 排序号
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * 备注
     */
    @Column(name = "remark", length = 512)
    private String remark;

    /**
     * 角色拥有的权限
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "syst_role_permission",
        joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private Set<Permission> permissions = new HashSet<>();

    /**
     * 角色关联的用户
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
}