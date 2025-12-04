package net.ijupiter.trading.system.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 系统配置查询DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
public class SystemConfigQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置类型
     */
    private String configType;

    /**
     * 是否系统内置配置
     */
    private Boolean isSystem;

    /**
     * 分页页码
     */
    private Integer pageNum = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 10;
}