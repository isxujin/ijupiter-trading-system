package net.ijupiter.trading.system.api.dtos;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 数据字典查询DTO
 * 
 * @author iJupiter
 * @version 1.0.1
 */
@Data
@Accessors(chain = true)
public class DataDictQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;

    /**
     * 分页页码
     */
    private Integer pageNum = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 10;
}