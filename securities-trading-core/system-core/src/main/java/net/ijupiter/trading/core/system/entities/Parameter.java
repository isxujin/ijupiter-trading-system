package net.ijupiter.trading.core.system.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.io.Serial;

/**
 * 系统参数实体
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
@Table(name = "syst_parameter")
public class Parameter extends BaseEntity<Parameter> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数编码，用于业务标识
     */
    @Column(name = "param_code", length = 64, nullable = false, unique = true)
    private String paramCode;

    /**
     * 参数名称
     */
    @Column(name = "param_name", length = 128, nullable = false)
    private String paramName;

    /**
     * 参数值
     */
    @Column(name = "param_value", length = 1024)
    private String paramValue;

    /**
     * 参数类型（STRING: 字符串, NUMBER: 数字, BOOLEAN: 布尔, JSON: JSON对象）
     */
    @Column(name = "param_type", length = 32, nullable = false)
    private String paramType = "STRING";

    /**
     * 默认值
     */
    @Column(name = "default_value", length = 512)
    private String defaultValue;

    /**
     * 参数分组
     */
    @Column(name = "param_group", length = 64)
    private String paramGroup;

    /**
     * 参数描述
     */
    @Column(name = "description", length = 512)
    private String description;

    /**
     * 是否为系统参数（0：否，1：是）
     */
    @Column(name = "is_system", nullable = false)
    private Integer isSystem = 0;

    /**
     * 是否可编辑（0：否，1：是）
     */
    @Column(name = "is_editable", nullable = false)
    private Integer isEditable = 1;

    /**
     * 排序号
     */
    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    /**
     * 参数状态（0：禁用，1：启用）
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 最后修改人
     */
    @Column(name = "last_modifier", length = 64)
    private String lastModifier;

    /**
     * 验证规则（正则表达式）
     */
    @Column(name = "validation_rule", length = 256)
    private String validationRule;

    /**
     * 参数选项（用于下拉选择等，JSON格式）
     */
    @Column(name = "options", length = 1024)
    private String options;
}