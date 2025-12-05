package net.ijupiter.trading.system.api.dtos;

import lombok.Data;
import lombok.experimental.Accessors;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.ijupiter.trading.common.dtos.BaseDTO;

/**
 * 数据字典DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
public class DataDictDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @NotNull(message = "字典ID不能为空")
    private String dictId;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;

}