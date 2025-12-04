package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.base.BaseEntityWithCustomId;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 操作员实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_operator")
@Comment("操作员表")
public class Operator extends BaseEntityWithCustomId {

    /**
     * 操作员ID
     */
    @Id
    @Column(name = "operator_id", length = 32)
    @Comment("操作员ID")
    private String operatorId;

    /**
     * 操作员名称
     */
    @Column(name = "operator_name", length = 64, nullable = false)
    @Comment("操作员名称")
    private String operatorName;

    /**
     * 登录名
     */
    @Column(name = "login_name", length = 64, nullable = false)
    @Comment("登录名")
    private String loginName;

    /**
     * 登录密码
     */
    @Column(name = "password", length = 128, nullable = false)
    @Comment("登录密码")
    private String password;

    /**
     * 联系电话
     */
    @Column(name = "phone", length = 32)
    @Comment("联系电话")
    private String phone;

    /**
     * 电子邮箱
     */
    @Column(name = "email", length = 64)
    @Comment("电子邮箱")
    private String email;

    /**
     * 状态（0-禁用，1-启用）
     */
    @Column(name = "status")
    @Comment("状态（0-禁用，1-启用）")
    private Integer status;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    @Comment("最后登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    @Column(name = "last_login_ip", length = 64)
    @Comment("最后登录IP")
    private String lastLoginIp;
}