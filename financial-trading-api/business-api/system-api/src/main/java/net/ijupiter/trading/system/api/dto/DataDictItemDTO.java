package net.ijupiter.trading.system.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据字典项DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
public class DataDictItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典项ID
     */
    @NotNull(message = "字典项ID不能为空")
    private String itemId;

    /**
     * 字典ID
     */
    @NotNull(message = "字典ID不能为空")
    private String dictId;

    /**
     * 字典项编码
     */
    @NotBlank(message = "字典项编码不能为空")
    private String itemCode;

    /**
     * 字典项名称
     */
    @NotBlank(message = "字典项名称不能为空")
    private String itemName;

    /**
     * 字典项值
     */
    @NotBlank(message = "字典项值不能为空")
    private String itemValue;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}