package net.ijupiter.trading.core.system.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统用户实体
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
@Table(name = "syst_user")
public class User extends BaseEntity<User> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号，用于业务标识
     */
    @Column(name = "user_code", length = 64, nullable = false, unique = true)
    private String userCode;

    /**
     * 用户名
     */
    @Column(name = "username", length = 64, nullable = false, unique = true)
    private String username;

    /**
     * 密码（加密存储）
     */
    @Column(name = "password", length = 128, nullable = false)
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 64, nullable = false)
    private String realName;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 128)
    private String email;

    /**
     * 手机号
     */
    @Column(name = "phone", length = 32)
    private String phone;

    /**
     * 用户状态（0：禁用，1：启用）
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 备注
     */
    @Column(name = "remark", length = 512)
    private String remark;

    /**
     * 用户所属角色
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "syst_user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();
}