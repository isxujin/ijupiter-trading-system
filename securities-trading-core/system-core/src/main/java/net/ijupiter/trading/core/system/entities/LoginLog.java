package net.ijupiter.trading.core.system.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统用户登录日志实体
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
@Table(name = "syst_login_log")
public class LoginLog extends BaseEntity<LoginLog> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "username", length = 64, nullable = false)
    private String username;

    /**
     * 登录IP
     */
    @Column(name = "login_ip", length = 64)
    private String loginIp;

    /**
     * 登录地点
     */
    @Column(name = "login_location", length = 128)
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Column(name = "browser", length = 128)
    private String browser;

    /**
     * 操作系统
     */
    @Column(name = "operating_system", length = 128)
    private String operatingSystem;

    /**
     * 登录状态（0：失败，1：成功）
     */
    @Column(name = "login_status", nullable = false)
    private Integer loginStatus;

    /**
     * 登录消息（失败原因等）
     */
    @Column(name = "login_message", length = 256)
    private String loginMessage;

    /**
     * 登录时间（重写父类createTime，便于业务理解）
     */
    @Column(name = "login_time", nullable = false)
    private LocalDateTime loginTime;

    /**
     * 登出时间
     */
    @Column(name = "logout_time")
    private LocalDateTime logoutTime;

    /**
     * 在线时长（分钟）
     */
    @Column(name = "online_duration")
    private Long onlineDuration;

    /**
     * 重写父类的prePersist方法，设置登录时间为当前时间
     */
    @PrePersist
    @Override
    protected void prePersist() {
        super.prePersist();
        this.loginTime = this.createTime;
    }
}