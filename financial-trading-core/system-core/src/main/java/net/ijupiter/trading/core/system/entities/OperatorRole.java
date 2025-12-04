package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 操作员角色关联实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sys_operator_role")
@Comment("操作员角色关联表")
public class OperatorRole {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment("主键ID")
    private Long id;

    /**
     * 操作员ID
     */
    @Column(name = "operator_id", length = 32, nullable = false)
    @Comment("操作员ID")
    private String operatorId;

    /**
     * 角色ID
     */
    @Column(name = "role_id", length = 32, nullable = false)
    @Comment("角色ID")
    private String roleId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Comment("创建时间")
    private LocalDateTime createTime;
}