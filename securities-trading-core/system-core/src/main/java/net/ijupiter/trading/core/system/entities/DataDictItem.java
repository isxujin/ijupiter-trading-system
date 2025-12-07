package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import net.ijupiter.trading.common.entities.BaseEntity;
import org.hibernate.annotations.Comment;

/**
 * 数据字典项实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "sys_data_dict_item")
@Comment("数据字典项表")
public class DataDictItem extends BaseEntity<DataDictItem> {
    /**
     * 字典编码
     */
    @Column(name = "dict_id", length = 36, nullable = false)
    @Comment("字典ID")
    private String dictId;

    /**
     * 字典项编码
     */
    @Column(name = "item_code", length = 64, nullable = false)
    @Comment("字典项编码")
    private String itemCode;

    /**
     * 字典项名称
     */
    @Column(name = "item_name", length = 64, nullable = false)
    @Comment("字典项名称")
    private String itemName;

    /**
     * 字典项值
     */
    @Column(name = "item_value", length = 128, nullable = false)
    @Comment("字典项值")
    private String itemValue;

    /**
     * 排序号
     */
    @Column(name = "sort_order")
    @Comment("排序号")
    private Integer sortOrder;

    /**
     * 状态（0-禁用，1-启用）
     */
    @Column(name = "status")
    @Comment("状态（0-禁用，1-启用）")
    private Integer status;
}