package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import net.ijupiter.trading.common.base.BaseEntityWithCustomId;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 系统配置实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sys_config")
@Comment("系统配置表")
public class SystemConfig  extends BaseEntityWithCustomId {

    /**
     * 配置ID
     */
    @Id
    @Column(name = "config_id", length = 32)
    @Comment("配置ID")
    private String configId;

    /**
     * 配置键
     */
    @Column(name = "config_key", length = 64, nullable = false)
    @Comment("配置键")
    private String configKey;

    /**
     * 配置值
     */
    @Column(name = "config_value", length = 512, nullable = false)
    @Comment("配置值")
    private String configValue;

    /**
     * 配置描述
     */
    @Column(name = "description", length = 256)
    @Comment("配置描述")
    private String description;

    /**
     * 配置类型
     */
    @Column(name = "config_type", length = 32)
    @Comment("配置类型")
    private String configType;

    /**
     * 是否系统内置配置
     */
    @Column(name = "is_system")
    @Comment("是否系统内置配置")
    private Boolean isSystem;

}