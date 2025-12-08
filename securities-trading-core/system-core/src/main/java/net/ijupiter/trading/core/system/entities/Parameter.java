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
    @Column(name = "param_value", length = 1024, nullable = false)
    private String paramValue;

    /**
     * 参数类型（string：字符串，number：数字，boolean：布尔，json：JSON对象）
     */
    @Column(name = "param_type", length = 32, nullable = false)
    private String paramType;

    /**
     * 参数分组
     */
    @Column(name = "param_group", length = 64)
    private String paramGroup;

    /**
     * 是否系统内置参数（0：否，1：是）
     */
    @Column(name = "is_system", nullable = false)
    private Integer isSystem = 0;

    /**
     * 参数状态（0：禁用，1：启用）
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 备注
     */
    @Column(name = "remark", length = 512)
    private String remark;
}