package net.ijupiter.trading.api.system.dtos;

import lombok.*;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.time.LocalDateTime;

/**
 * 数据字典项数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class DictionaryItemDTO extends BaseDTO<DictionaryItemDTO> {

    /**
     * 字典项编码，用于业务标识
     */
    private String itemCode;

    /**
     * 字典项值
     */
    private String itemValue;

    /**
     * 字典项标签
     */
    private String itemLabel;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 字典项状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

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
     * 所属字典ID
     */
    private Long dictionaryId;

    /**
     * 所属字典编码
     */
    private String dictCode;

    /**
     * 所属字典名称
     */
    private String dictName;

    /**
     * 是否为默认值
     */
    private Boolean isDefault;

    /**
     * 字典项样式（如颜色等）
     */
    private String cssClass;
}