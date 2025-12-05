package net.ijupiter.trading.system.api.dtos;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统配置DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
public class SystemConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @NotNull(message = "配置ID不能为空")
    private String configId;

    /**
     * 配置键
     */
    @NotBlank(message = "配置键不能为空")
    private String configKey;

    /**
     * 配置值
     */
    @NotBlank(message = "配置值不能为空")
    private String configValue;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 配置类型
     */
    private String configType;

    /**
     * 是否系统内置配置
     */
    private Boolean isSystem;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}