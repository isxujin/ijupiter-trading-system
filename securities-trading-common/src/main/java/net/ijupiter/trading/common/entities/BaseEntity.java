package net.ijupiter.trading.common.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Accessors(chain = true)
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity<T extends BaseEntity<T>> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 实体ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", length = 36, nullable = false, unique = true)
    protected Long id;

    /**
     * 公共创建时间
     */
    @Column(name = "create_time")
    protected LocalDateTime createTime;

    /**
     * 公共更新时间
     */
    @Column(name = "update_time")
    protected LocalDateTime updateTime;

    /**
     * 版本号（用于乐观锁）
     */
    @Column(name = "entity_version")
    protected Long version;

    /**
     * JPA 持久化前回调（新增实体时触发）
     * 作用：自动设置创建时间、初始化更新时间
     */
    @PrePersist
    protected void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        // 创建时间仅在首次保存时赋值（避免覆盖）
        if (this.createTime == null) {
            this.createTime = now;
        }
        // 初始化更新时间为当前时间
        this.updateTime = now;
    }

    /**
     * JPA 更新前回调（修改实体时触发）
     * 作用：自动刷新更新时间
     */
    @PreUpdate
    protected void preUpdate() {
        // 属性变动时，更新时间刷新为当前系统时间
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 将源对象转换为当前 Entity 实例
     * @param source 源对象
     * @return 当前实例（已填充源对象属性）
     */
    @SuppressWarnings("unchecked")
    public T convertFrom(Object source) {
        // 1. 校验源源对象是否为null
        if (source == null) {
            throw new IllegalArgumentException("转换失败：源对象不能为null");
        }
        try {
            // 2. 核心：将源对象属性拷贝到当前实例（this）中
            BeanUtils.copyProperties(source, this);
        } catch (Exception e) {
            // 3. 捕获并处理属性拷贝过程中可能发生的异常
            throw new RuntimeException("转换失败：属性拷贝过程中发生错误", e);
        }
        // 4. 返回当前实例（泛型保证类型安全，无需强制转换）
        return (T) this;
    }
}