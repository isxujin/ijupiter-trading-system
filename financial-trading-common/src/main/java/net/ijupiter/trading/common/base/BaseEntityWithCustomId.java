package net.ijupiter.trading.common.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 自定义ID字段名称的实体基类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class BaseEntityWithCustomId {

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false, updatable = false)
    protected LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    protected LocalDateTime updateTime;

    /**
     * 创建者ID
     */
    @Column(name = "creator_id", updatable = false, length = 50)
    protected String creatorId;

    /**
     * 更新者ID
     */
    @Column(name = "updater_id", length = 50)
    protected String updaterId;

    /**
     * 版本号，用于乐观锁
     */
    @Version
    @Column(name = "version", nullable = false)
    protected Long version;

    /**
     * 是否已删除，逻辑删除标记
     */
    @Column(name = "deleted", nullable = false)
    protected Boolean deleted = Boolean.FALSE;

    /**
     * 预持久化回调
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createTime == null) {
            createTime = now;
        }
        if (deleted == null) {
            deleted = Boolean.FALSE;
        }
        if (version == null) {
            version = 0L;
        }
    }

    /**
     * 预更新回调
     */
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}