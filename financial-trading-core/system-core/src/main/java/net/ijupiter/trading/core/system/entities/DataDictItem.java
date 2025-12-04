package net.ijupiter.trading.core.system.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 数据字典项实体
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "sys_data_dict_item")
@Comment("数据字典项表")
public class DataDictItem {

    /**
     * 字典项ID
     */
    @Id
    @Column(name = "item_id", length = 32)
    @Comment("字典项ID")
    private String itemId;

    /**
     * 字典ID
     */
    @Column(name = "dict_id", length = 32, nullable = false)
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

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Comment("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @Comment("更新时间")
    private LocalDateTime updateTime;
}