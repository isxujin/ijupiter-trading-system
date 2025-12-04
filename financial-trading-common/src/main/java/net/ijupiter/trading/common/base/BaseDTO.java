package net.ijupiter.trading.common.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * 数据传输对象基类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {

    /**
     * 主键ID
     */
    protected String id;

    /**
     * 创建时间
     */
    protected LocalDateTime createTime;

    /**
     * 更新时间
     */
    protected LocalDateTime updateTime;

    /**
     * 创建者ID
     */
    protected String creatorId;

    /**
     * 更新者ID
     */
    protected String updaterId;

    /**
     * 版本号，用于乐观锁
     */
    protected Long version;

    /**
     * 是否已删除，逻辑删除标记
     */
    protected Boolean deleted;
}