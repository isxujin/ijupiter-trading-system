package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;
import org.hibernate.annotations.Comment;



/**
 * 数据字典实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "sys_data_dict")
@Comment("数据字典表")
public class DataDict extends BaseEntity<DataDict> {
    /**
     * 字典编码
     */
    @Column(name = "dict_code", length = 64, nullable = false)
    @Comment("字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @Column(name = "dict_name", length = 64, nullable = false)
    @Comment("字典名称")
    private String dictName;

    /**
     * 字典描述
     */
    @Column(name = "description", length = 256)
    @Comment("字典描述")
    private String description;

    /**
     * 状态（0-禁用，1-启用）
     */
    @Column(name = "status")
    @Comment("状态（0-禁用，1-启用）")
    private Integer status;

}