package net.ijupiter.trading.api.system.dtos;

import lombok.*;
import net.ijupiter.trading.common.dtos.BaseDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据字典数据传输对象
 * 
 * @author ijupiter
 * @since 1.0.1-SNAPSHOT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class DictionaryDTO extends BaseDTO<DictionaryDTO> {

    /**
     * 字典编码，用于业务标识
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型（system：系统字典，business：业务字典）
     */
    private String dictType;

    /**
     * 字典状态（0：禁用，1：启用）
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
     * 字典项列表
     */
    private List<DictionaryItemDTO> items;

    /**
     * 字典项数量
     */
    private Integer itemCount;

    /**
     * 是否为系统字典（不可删除）
     */
    private Boolean isSystemDict;
}