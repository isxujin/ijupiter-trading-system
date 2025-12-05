package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;
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
public class OperatorRole  extends BaseEntity<OperatorRole> {
    /**
     * 操作员ID
     */
    @Column(name = "operator_id", length = 36, nullable = false)
    @Comment("操作员ID")
    private String operatorId;

    /**
     * 角色ID
     */
    @Column(name = "role_id", length = 36, nullable = false)
    @Comment("角色ID")
    private String roleId;

}