package net.ijupiter.trading.api.system.dtos;

import lombok.*;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.time.LocalDateTime;

/**
 * 系统参数数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ParameterDTO extends BaseDTO<ParameterDTO> {

    /**
     * 参数编码，用于业务标识
     */
    private String paramCode;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 参数类型（STRING: 字符串, NUMBER: 数字, BOOLEAN: 布尔, JSON: JSON对象）
     */
    private String paramType;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 参数分组
     */
    private String paramGroup;

    /**
     * 参数描述
     */
    private String description;

    /**
     * 是否为系统参数（0：否，1：是）
     */
    private Integer isSystem;

    /**
     * 是否可编辑（0：否，1：是）
     */
    private Integer isEditable;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 参数状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 最后修改人
     */
    private String lastModifier;

    /**
     * 验证规则（正则表达式）
     */
    private String validationRule;

    /**
     * 参数选项（用于下拉选择等，JSON格式）
     */
    private String options;
}