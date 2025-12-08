package net.ijupiter.trading.core.system.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import net.ijupiter.trading.common.entities.BaseEntity;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统数据字典实体（主表）
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
@Table(name = "syst_dictionary")
public class Dictionary extends BaseEntity<Dictionary> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典编码，用于业务标识
     */
    @Column(name = "dict_code", length = 64, nullable = false, unique = true)
    private String dictCode;

    /**
     * 字典名称
     */
    @Column(name = "dict_name", length = 128, nullable = false)
    private String dictName;

    /**
     * 字典类型（system：系统字典，business：业务字典）
     */
    @Column(name = "dict_type", length = 32, nullable = false)
    private String dictType;

    /**
     * 字典状态（0：禁用，1：启用）
     */
    @Column(name = "status", nullable = false)
    private Integer status = 1;

    /**
     * 备注
     */
    @Column(name = "remark", length = 512)
    private String remark;

    /**
     * 字典项列表
     */
    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DictionaryItem> items = new HashSet<>();
}