package net.ijupiter.trading.core.system.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.io.Serial;

/**
 * 系统数据字典项实体（从表）
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
@Table(name = "syst_dictionary_item")
public class DictionaryItem extends BaseEntity<DictionaryItem> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典项编码，用于业务标识
     */
    @Column(name = "item_code", length = 64, nullable = false)
    private String itemCode;

    /**
     * 字典项值
     */
    @Column(name = "item_value", length = 256, nullable = false)
    private String itemValue;

    /**
     * 字典项标签
     */
    @Column(name = "item_label", length = 128, nullable = false)
    private String itemLabel;

    /**
     * 排序号
     */
    @Column(name = "sort_order")
    private Integer sortOrder;

    /**
     * 字典项状态（0：禁用，1：启用）
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 备注
     */
    @Column(name = "remark", length = 512)
    private String remark;

    /**
     * 所属字典
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dictionary_id", referencedColumnName = "id", nullable = false)
    private Dictionary dictionary;
}